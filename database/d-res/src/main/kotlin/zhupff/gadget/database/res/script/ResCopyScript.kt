package zhupff.gadget.database.res.script

import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FFmpegFrameRecorder
import org.bytedeco.javacv.FFmpegLogCallback
import zhupff.gadget.basic.file.isImage
import zhupff.gadget.basic.file.isVideo
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class ResCopyScript(
    val fromDir: File,
    val toDir: File,
) : Runnable {

    companion object {
        private val SCALED_LONGER_LENGHT = 1920
        private val SCALED_SHORTER_LENGHT = 1080
    }

    init {
        if (!fromDir.exists()) {
            throw IllegalArgumentException("${fromDir.absolutePath} does not exist")
        }
        if (toDir.exists()) {
            toDir.deleteRecursively()
        }
        toDir.mkdirs()
    }


    override fun run() {
        fromDir.listFiles().forEach { fromFile ->
            if (fromFile.isImage()) {
                val toFile = toDir.resolve("${fromFile.nameWithoutExtension}.jpeg")
                val fromImage = ImageIO.read(fromFile)
                val scaledSize = getScaledSize(fromImage.width, fromImage.height)
                val scaledImage = fromImage.getScaledInstance(scaledSize.first, scaledSize.second, Image.SCALE_DEFAULT)
                val toImage = BufferedImage(scaledSize.first, scaledSize.second, BufferedImage.TYPE_INT_RGB)
                toImage.graphics.let {
                    it.drawImage(scaledImage, 0, 0, null)
                    it.dispose()
                }
                ImageIO.write(toImage, "JPEG", toFile)
            } else if (fromFile.isVideo()) {
                val toFile = toDir.resolve(fromFile.name)
                val grabber = FFmpegFrameGrabber.createDefault(fromFile)
                try {
                    grabber.start()
                    val duration = grabber.formatContext.duration()
                    val scaledSize = getScaledSize(grabber.imageWidth, grabber.imageHeight)
                    val recorder = FFmpegFrameRecorder(toFile, scaledSize.first, scaledSize.second, grabber.audioChannels)
                    try {
                        recorder.format = "mp4"
                        recorder.frameRate = grabber.frameRate
                        recorder.sampleRate = grabber.sampleRate
                        recorder.videoCodec = grabber.videoCodec
                        recorder.audioCodec = grabber.audioCodec
                        recorder.videoBitrate = grabber.videoBitrate
                        recorder.audioBitrate = grabber.audioBitrate
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
                                println("process 100%, total frames = $count")
                                recorder
                                break
                            }
                            count++
                            val p = frame.timestamp.toDouble() / duration.toDouble() * 100
                            if (p > percent) {
                                percent = p
                            }
                            println("process %.2f%%".format(percent))
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
            }
        }
    }

    private fun getScaledSize(width: Int, height: Int): Pair<Int, Int> {
        var targetWidth = width
        var targetHeight = height
        if (width >= height) {
            if (width > SCALED_LONGER_LENGHT || height > SCALED_SHORTER_LENGHT) {
                if (width.toFloat() / SCALED_LONGER_LENGHT.toFloat() > height.toFloat() / SCALED_SHORTER_LENGHT.toFloat()) {
                    targetWidth = SCALED_LONGER_LENGHT
                    targetHeight = height * SCALED_LONGER_LENGHT / width
                } else {
                    targetHeight = SCALED_SHORTER_LENGHT
                    targetWidth = width * SCALED_SHORTER_LENGHT / height
                }
            }
        } else { // width < height
            if (width > SCALED_SHORTER_LENGHT || height > SCALED_LONGER_LENGHT) {
                if (width.toFloat() / SCALED_SHORTER_LENGHT.toFloat() > height.toFloat() / SCALED_LONGER_LENGHT.toFloat()) {
                    targetWidth = SCALED_SHORTER_LENGHT
                    targetHeight = height * SCALED_SHORTER_LENGHT / width
                } else {
                    targetHeight = SCALED_LONGER_LENGHT
                    targetWidth = width * SCALED_LONGER_LENGHT / height
                }
            }
        }

        return targetWidth to targetHeight
    }
}