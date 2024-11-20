package zhupff.gadget.server.res.singlevideo

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import zhupff.gadget.server.basic.json.gson
import zhupff.gadget.server.model.single.SingleVideo
import zhupff.gadget.server.res.script.LOCAL_RES
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.min

@Component
class SingleVideoDB {

    companion object  : MutableList<SingleVideo> by ArrayList() {

        private val isShuffled = AtomicBoolean(true)

        private val problemVideoIDs = HashSet<String>()

        fun doShuffle() {
            if (isShuffled.compareAndSet(false, true)) {
                synchronized(SingleVideoDB) {
                    println("doShuffle")
                    this.removeIf {
                        it.id in problemVideoIDs
                    }
                    this.shuffle()
                }
            }
        }

        fun getItems(lastVideoID: String?, size: Int): List<SingleVideo> {
            if (size <= 0) return emptyList()
            if (lastVideoID.isNullOrEmpty() || lastVideoID == "0") {
                isShuffled.set(false)
                return subList(0, min(size, this.size))
            }
            val index = indexOfFirst { it.id == lastVideoID }
            if (index < 0 || index == this.lastIndex) {
                return emptyList()
            }
            isShuffled.set(false)
            val endIndex = min(index + 1 + size, this.size)
            return subList(index + 1, endIndex)
        }

        fun record(videoID: String) {
            try {
                if (problemVideoIDs.add(videoID)) {
                    val problemVideosFile = LOCAL_RES.resolve("singlevideo/problem_videos.txt")
                    if (!problemVideosFile.exists()) {
                        problemVideosFile.createNewFile()
                    }
                    problemVideosFile.appendText(videoID)
                    problemVideosFile.appendText("\n")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val init = AtomicBoolean(false)

    @PostConstruct
    fun init() {
        synchronized(this) {
            if (init.compareAndSet(false, true)) {
                doInit()
            }
        }
    }

    private fun doInit() {
        try {
            val problemVideosFile = LOCAL_RES.resolve("singlevideo/problem_videos.txt")
            if (!problemVideosFile.exists()) {
                problemVideosFile.createNewFile()
            }
            problemVideosFile.readLines(Charsets.UTF_8).forEach { line ->
                problemVideoIDs.add(line)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("Unsolve problem video size = ${problemVideoIDs.size}")
        try {
            val dirs = LOCAL_RES.resolve("singlevideo").listFiles().filter {
                it.name !in problemVideoIDs && it.extension != "txt"
            }
            problemVideoIDs.clear()
            dirs?.forEach { dir ->
                try {
                    val json = dir.resolve("info.json").readText(Charsets.UTF_8)
                    val singleVideo = gson.fromJson(json, SingleVideo::class.java)
                    if (singleVideo != null) {
                        add(singleVideo)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("Single Video size = ${size}")
    }
}