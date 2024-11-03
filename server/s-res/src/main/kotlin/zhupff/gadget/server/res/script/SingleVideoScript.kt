package zhupff.gadget.server.res.script

import org.jcodec.api.awt.AWTFrameGrab
import org.jcodec.common.io.NIOUtils
import org.springframework.util.DigestUtils
import org.springframework.util.FileCopyUtils
import zhupff.gadget.server.basic.json.gson
import zhupff.gadget.server.model.single.SingleVideo
import java.io.File
import java.io.FileNotFoundException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import javax.imageio.ImageIO

private fun main() {
    SingleVideoScript().run()
}

class SingleVideoScript : Runnable {

    private val fromDir = ORIGIN_RES.resolve("singlevideo").also {
        if (!it.exists() || !it.isDirectory) {
            throw FileNotFoundException("${it.absolutePath} does not exists!")
        }
    }

    private val toDir = LOCAL_RES.resolve("singlevideo").also {
        if (!it.exists()) {
            it.mkdirs()
        }
        if (!it.isDirectory) {
            throw FileNotFoundException("${it.absolutePath} is not a directory!")
        }
    }

    private val archiveDir = fromDir.resolve("archive").also {
        it.mkdirs()
    }

    private val executor by lazy { Executors.newFixedThreadPool(4) }

    private val lock = Any()


    override fun run() {
        val files = fromDir.listFiles()?.filter {
            it.extension in VIDEO_EXT
        } ?: emptyList()
        val latch = CountDownLatch(files.size)

        files.forEach { file ->
            executor.execute {
                val start = System.currentTimeMillis()
                val md5 = file.inputStream().use {
                    DigestUtils.md5DigestAsHex(it)
                }
                val end = System.currentTimeMillis()
                println("process ${file.name}, md5=$md5, cast=${end - start}ms")

                synchronized(lock) {
                    val toMD5Dir = toDir.resolve(md5)
                    if (toMD5Dir.exists() && toMD5Dir.isDirectory && !toMD5Dir.listFiles().isNullOrEmpty()) {
                        println("${toMD5Dir.absolutePath} already exists")
                    } else {
                        toMD5Dir.mkdirs()
                        val archiveFile = archiveDir.resolve("${md5}.${file.extension}")
                        val videoFile = toMD5Dir.resolve("video.${file.extension}")
                        val coverFile = toMD5Dir.resolve("cover.jpeg")
                        val jsonFile = toMD5Dir.resolve("info.json")

                        try {
                            NIOUtils.readableChannel(file).use { channel ->
                                val grap = AWTFrameGrab.createAWTFrameGrab(channel)
                                val frame = grap.frame
                                ImageIO.write(frame, "JPEG", coverFile)
                                val singleVideo = SingleVideo(
                                    id = md5,
                                    width = frame.width,
                                    height = frame.height,
                                    duration = (grap.videoTrack.meta.totalDuration * 1000).toLong(),
                                    url = "res" + videoFile.absolutePath
                                        .replace(LOCAL_RES.absolutePath, "")
                                        .replace('\\', File.separatorChar),
                                    cover = "res" + coverFile.absolutePath
                                        .replace(LOCAL_RES.absolutePath, "")
                                        .replace('\\', File.separatorChar),
                                )
                                jsonFile.writeText(gson.toJson(singleVideo), Charsets.UTF_8)
                                FileCopyUtils.copy(file, videoFile)
                                if (!archiveFile.exists()) {
                                    FileCopyUtils.copy(file, archiveFile)
                                }
                                file.deleteOnExit()
                            }
                        } catch (e: Exception) {
                            println("process ${file.name} exception: ${e.message}")
                            e.printStackTrace()
                            toMD5Dir.deleteOnExit()
                        }
                    }
                }
                latch.countDown()
            }
        }

        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            executor.shutdown()
        }
    }
}