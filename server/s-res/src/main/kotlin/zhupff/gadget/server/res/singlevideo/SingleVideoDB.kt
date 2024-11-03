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

        fun doShuffle() {
            synchronized(SingleVideoDB) {
                println("doShuffle")
                this.shuffle()
            }
        }

        fun getItems(lastVideoID: String?, size: Int): List<SingleVideo> {
            if (size <= 0) return emptyList()
            if (lastVideoID.isNullOrEmpty()) {
                return subList(0, min(size, this.size))
            }
            val index = indexOfFirst { it.id == lastVideoID }
            if (index < 0 || index == this.lastIndex) {
                return emptyList()
            }
            val endIndex = min(index + 1 + size, this.size)
            return subList(index + 1, endIndex)
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
            val dirs = LOCAL_RES.resolve("singlevideo").listFiles()
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