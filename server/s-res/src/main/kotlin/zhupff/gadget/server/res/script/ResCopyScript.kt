package zhupff.gadget.server.res.script

import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FFmpegFrameRecorder
import org.bytedeco.javacv.FFmpegLogCallback
import org.springframework.util.FileCopyUtils
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileNotFoundException
import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO
import kotlin.math.min

private fun main() {
    try {
        arrayOf(
            ResCopyScript("test/vol_1/").call()
        ).forEach { latch ->
            latch.await()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        ResCopyScript.shutdown()
    }
}



private class ResCopyScript(
    resolvePath: String,
) : Runnable, Callable<CountDownLatch> {

    companion object {
        private val L = 1920
        private val S = 1080
        private val F = 24
        private val R = 4_800_000

        private val imageExecutor by lazy { Executors.newFixedThreadPool(2) }
        private val videoExecutor by lazy { Executors.newFixedThreadPool(2) }
        fun shutdown() {
            try {
                imageExecutor.shutdown()
                videoExecutor.shutdown()
                if (!imageExecutor.awaitTermination(3, TimeUnit.MINUTES)) {
                    imageExecutor.shutdownNow()
                }
                if (!videoExecutor.awaitTermination(10, TimeUnit.MINUTES)) {
                    videoExecutor.shutdownNow()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                imageExecutor.shutdownNow()
                videoExecutor.shutdownNow()
            }
        }
    }

    init {
        if (resolvePath.startsWith('/')) {
            throw IllegalArgumentException("resolvePath must not start with /")
        }
        if (!resolvePath.endsWith('/')) {
            throw IllegalArgumentException("resolvePath must end with /")
        }
    }

    private val originResDir = ORIGIN_RES.resolve(resolvePath).also {
        if (!it.exists()) {
            throw FileNotFoundException("${it.absolutePath} does not exist!")
        }
    }

    private val localResDir = LOCAL_RES.resolve(resolvePath).also {
        if (it.exists()) {
            it.deleteRecursively()
        }
        it.mkdirs()
    }

    private val tasks = ArrayList<Task>()

    init {
        originResDir.listFiles()?.forEach { file ->
            val name = file.nameWithoutExtension
            val ext = file.extension
            if (name.contains('[') && name.contains(']')) {
                // pass
            } else {
                if (ext in IMAGE_EXT) {
                    tasks.add(ImageTask(file))
                } else if (ext in VIDEO_EXT) {
                    tasks.add(VideoTask(file))
                }
            }
        }
    }

    private val latch = CountDownLatch(tasks.size)

    override fun run() {
        call()
        try {
            latch.await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return
    }

    override fun call(): CountDownLatch {
        tasks.forEach { task ->
            if (task is ImageTask) {
                imageExecutor.execute(task)
            } else if (task is VideoTask) {
                videoExecutor.execute(task)
            }
        }
        return latch
    }

    private fun scale(width: Int, height: Int): Pair<Int, Int> {
        var targetWidth = width
        var targetHeight = height
        if (width >= height) {
            if (width > L || height > S) {
                if (width.toFloat() / L.toFloat() > height.toFloat() / S.toFloat()) {
                    targetWidth = L
                    targetHeight = height * L / width
                } else {
                    targetHeight = S
                    targetWidth = width * S / height
                }
            }
        } else { // width < height
            if (width > S || height > L) {
                if (width.toFloat() / S.toFloat() > height.toFloat() / L.toFloat()) {
                    targetWidth = S
                    targetHeight = height * S / width
                } else {
                    targetHeight = L
                    targetWidth = width * L / height
                }
            }
        }
        return targetWidth to targetHeight
    }
    


    private abstract inner class Task(
        val srcFile: File,
    ) : Runnable

    private inner class ImageTask(
        srcFile: File,
    ) : Task(
        srcFile,
    ) {
        override fun run() {
            val startTimestamp = System.currentTimeMillis()
            val srcFileName = srcFile.name
            println("processing image: ${srcFileName}")
            try {
                val dstFile = localResDir.resolve("${srcFile.nameWithoutExtension}.jpeg")
                val srcImage = ImageIO.read(srcFile)
                val scaledSize = scale(srcImage.width, srcImage.height)
                val scaledImage = srcImage.getScaledInstance(scaledSize.first, scaledSize.second, Image.SCALE_DEFAULT)
                val dstImage = BufferedImage(scaledSize.first, scaledSize.second, BufferedImage.TYPE_INT_RGB)
                dstImage.graphics.let { graphics ->
                    graphics.drawImage(scaledImage, 0, 0, null)
                    graphics.dispose()
                }
                ImageIO.write(dstImage, "JPEG", dstFile)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                latch.countDown()
            }
            val endTimestamp = System.currentTimeMillis()
            println("process image: ${srcFileName} DONE, cast=${endTimestamp - startTimestamp}")
        }
    }

    private inner class VideoTask(
        srcFile: File,
    ) : Task(
        srcFile,
    ) {
        override fun run() {
            justCopy()
        }

        private fun compressAndCopy() {
            val startTimestamp = System.currentTimeMillis()
            val srcFileName = srcFile.name
            println("processing video: ${srcFileName}")
            try {
                val dstFile = localResDir.resolve("${srcFile.nameWithoutExtension}.mp4")
                val grabber = FFmpegFrameGrabber.createDefault(srcFile)
                try {
                    grabber.start()
                    val duration = grabber.formatContext.duration()
                    val scaledSize = scale(grabber.imageWidth, grabber.imageHeight)
                    val recorder = FFmpegFrameRecorder(dstFile, scaledSize.first, scaledSize.second, grabber.audioChannels)
                    try {
                        recorder.format = "mp4"
//                        recorder.frameRate = grabber.frameRate
                        recorder.frameRate = min(F.toDouble(), grabber.frameRate)
                        recorder.sampleRate = grabber.sampleRate
                        recorder.gopSize = recorder.frameRate.toInt()
                        recorder.videoCodec = grabber.videoCodec
                        recorder.audioCodec = grabber.audioCodec
//                        recorder.videoBitrate = grabber.videoBitrate
//                        recorder.audioBitrate = grabber.audioBitrate
                        recorder.videoBitrate = min(R, grabber.videoBitrate)
                        recorder.audioBitrate = min(R, grabber.audioBitrate)
                        recorder.aspectRatio = grabber.aspectRatio
//                        recorder.pixelFormat = grabber.pixelFormat
                        recorder.pixelFormat = org.bytedeco.ffmpeg.global.avutil.AV_PIX_FMT_YUV420P
                        recorder.videoOptions = grabber.videoOptions
                        recorder.audioOptions = grabber.audioOptions
                        recorder.videoQuality = org.bytedeco.ffmpeg.global.avutil.FF_LAMBDA_SHIFT.toDouble()
                        FFmpegLogCallback.set()
                        recorder.start()

                        var count = 0
                        var percent = 0.0
                        while (true) {
                            var frame = grabber.grabFrame()
                            if (frame == null) {
                                println("processed 100%, total frames = $count")
                                recorder
                                break
                            }
                            count++
                            val p = frame.timestamp.toDouble() / duration.toDouble() * 100
                            frame.keyFrame = true
                            if (p - percent > 0.01) {
                                percent = p
                                println("processing ${srcFileName} %.2f%%".format(percent))
                            }
                            recorder.record(frame)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        recorder.close()
                    }
//                    println("videoCodec=${grabber.videoCodec} videoCodecName=${grabber.videoCodecName}")
//                    println("audioCodec=${grabber.audioCodec} audioCodecName=${grabber.audioCodecName}")
//                    println("width=${grabber.imageWidth} height=${grabber.imageHeight} channels=${grabber.audioChannels}")
//                    println("format=${grabber.format} pixelFormat=${grabber.pixelFormat} sampleFormat=${grabber.sampleFormat}")
//                    println("frameRate=${grabber.frameRate} sampleRate=${grabber.sampleRate}")
//                    println("lengthInVideoFrames${grabber.lengthInVideoFrames} lengthInAudioFrames${grabber.lengthInAudioFrames}")
//                    println("videoBitrate=${grabber.videoBitrate} audioBitrate=${grabber.audioBitrate} aspectRatio=${grabber.aspectRatio}")
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    grabber.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                latch.countDown()
            }
            val endTimestamp = System.currentTimeMillis()
            println("process video: ${srcFileName} DONE, cast=${endTimestamp - startTimestamp}")
        }

        private fun justCopy() {
            val startTimestamp = System.currentTimeMillis()
            val srcFileName = srcFile.name
            println("processing video: ${srcFileName}")
            try {
                val dstFile = localResDir.resolve("${srcFile.nameWithoutExtension}.mp4")
                FileCopyUtils.copy(srcFile, dstFile)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                latch.countDown()
            }
            val endTimestamp = System.currentTimeMillis()
            println("process video: ${srcFileName} DONE, cast=${endTimestamp - startTimestamp}")
        }
    }
}