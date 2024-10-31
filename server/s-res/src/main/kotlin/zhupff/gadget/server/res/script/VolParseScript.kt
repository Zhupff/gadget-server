package zhupff.gadget.server.res.script

import org.jcodec.api.awt.AWTFrameGrab
import org.jcodec.common.io.NIOUtils
import java.io.File
import java.io.FileNotFoundException
import javax.imageio.ImageIO

private fun main() {
    VolParseScript("test/vol_1/").run()
}



class VolParseScript(
    val resolvePath: String,
) : Runnable {

    companion object {
        private val LOCAL_RES = File("").resolve("localres").also {
            if (!it.exists()) {
                throw FileNotFoundException("${it.absolutePath} NOT FOUND!")
            }
        }

        private val IMAGE_SUFFIX = arrayOf("jfif", "jpeg", "jpg", "png")
        private val VIDEO_SUFFIX = arrayOf("mp4")
    }

    init {
        if (resolvePath.startsWith('/')) {
            throw IllegalArgumentException("resolvePath must not start with /")
        }
        if (!resolvePath.endsWith('/')) {
            throw IllegalArgumentException("resolvePath must end with /")
        }
    }

    private val volDir = LOCAL_RES.resolve(resolvePath).also {
        if (!it.exists()) {
            throw FileNotFoundException("${it.absolutePath} does not exist!")
        }
    }

    private val files = volDir.listFiles()?.filter { file ->
        var filter = false
        val name = file.nameWithoutExtension
        val ext = file.extension
        if (name.contains('[') && name.contains(']')) {
            // pass
        } else if (name.endsWith(".cover")) {
            // pass
        } else {
            if (ext in IMAGE_SUFFIX) {
                filter = true
            } else if (ext in VIDEO_SUFFIX) {
                filter = true
            }
        }
        filter
    }

    override fun run() {
        if (files.isNullOrEmpty()) {
            println("${volDir.absolutePath} do not need to process!")
            return
        }

        val contentsBuilder = StringBuilder()
        files.forEachIndexed { index, file ->
            val ext = file.extension
            if (ext in IMAGE_SUFFIX) {
                contentsBuilder.appendLine(parseImage(file, index + 1))
            } else if (ext in VIDEO_SUFFIX) {
                contentsBuilder.appendLine(parseVideo(file, index + 1))
            }
        }

        val vol = volDir.name.substringAfterLast('_').toIntOrNull() ?: -1
        val ts = System.currentTimeMillis()
        val template = """
            newAlbum(
                staticID = $vol,
                path = "${resolvePath}",
                tags = arrayOf(),
            ).contents { arrayOf(
                ${contentsBuilder.toString().trim()}
            ) }
        """.trimIndent()
        volDir.resolve("[${ts}].txt").writeText(template)
    }

    private fun parseImage(file: File, id: Int): String {
        val imageIO = ImageIO.read(file)
        return """
            newImage(
                staticID = $id,
                file = "${file.name}",
                width = ${imageIO.width},
                height = ${imageIO.height},
            ),
        """.trimIndent().also {
            imageIO.flush()
        }
    }

    private fun parseVideo(file: File, id: Int): String {
        val channel = NIOUtils.readableChannel(file)
        val grap = AWTFrameGrab.createAWTFrameGrab(channel)
        val frame = grap.frame
        val cover = file.parentFile.resolve("${file.nameWithoutExtension}.cover.jpeg")
        if (cover.exists()) cover.delete()
        ImageIO.write(frame, "JPEG", cover)

        return """
            newVideo(
                staticID = $id,
                file = "${file.name}",
                width = ${frame.width},
                height = ${frame.height},
                duration = ${(grap.videoTrack.meta.totalDuration * 1000).toLong()},
            ),
        """.trimIndent().also {
            channel.close()
        }
    }
}