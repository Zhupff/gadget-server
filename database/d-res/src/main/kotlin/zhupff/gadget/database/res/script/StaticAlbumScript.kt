package zhupff.gadget.database.res.script

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import org.jcodec.api.awt.AWTFrameGrab
import org.jcodec.common.io.NIOUtils
import zhupff.gadget.basic.file.RES_DIR
import zhupff.gadget.basic.file.isImage
import zhupff.gadget.basic.file.isVideo
import zhupff.gadget.database.model.statics.StaticAlbum
import zhupff.gadget.database.model.statics.StaticImage
import zhupff.gadget.database.model.statics.StaticVideo
import java.io.File
import java.io.FileNotFoundException
import javax.imageio.ImageIO

class StaticAlbumScript(val album: StaticAlbum) : Runnable {

    private val albumDir = RES_DIR.resolve(album.path).also { dir ->
        if (!dir.exists()) {
            throw FileNotFoundException("${dir.absolutePath} not found")
        }
        if (!dir.isDirectory) {
            throw IllegalArgumentException("${dir.absolutePath} is not a directory")
        }
    }

    private val files = albumDir.listFiles().filter {
        it.isVideo() || (it.isImage() && !it.nameWithoutExtension.endsWith("_cover"))
    }.onEach {
        println(it)
    }

    private val content = ArrayList<String>()


    override fun run() {
        val ts = System.currentTimeMillis()
        FileSpec.builder("", "$ts")
            .addType(
                TypeSpec.classBuilder("C$ts")
                    .also { tsBuilder ->
                        files.forEach { file ->
                            if (file.isImage()) {
                                addImageProperty(tsBuilder, file)
                                content.add(file.nameWithoutExtension)
                            } else if (file.isVideo()) {
                                addVideoProperty(tsBuilder, file)
                                content.add(file.nameWithoutExtension)
                            }
                        }
                    }
                    .addProperty(
                        PropertySpec.builder("content", Array::class.asClassName().parameterizedBy(ANY), KModifier.OVERRIDE)
                            .initializer(CodeBlock.of(
                                """
                                    arrayOf(
                                    ${getContentCodeBlock()}
                                    )
                                """.trimIndent()
                            ))
                            .build()
                    )
                    .build()
            )
            .build()
            .writeTo(albumDir)
    }

    private fun addImageProperty(tsBuilder: TypeSpec.Builder, file: File) {
        val imageIO = ImageIO.read(file)
        tsBuilder.addProperty(
            PropertySpec.builder(file.nameWithoutExtension, StaticImage::class)
                .addAnnotation(Transient::class)
                .initializer(
                    CodeBlock.of(
                    """
                        newImage(
                            fileName = %S,
                            width = ${imageIO.width},
                            height = ${imageIO.height},
                        )
                    """.trimIndent(),
                    "${file.name}",
                ))
                .build()
        )
    }

    private fun addVideoProperty(tsBuilder: TypeSpec.Builder, file: File) {
        val channel = NIOUtils.readableChannel(file)
        val grap = AWTFrameGrab.createAWTFrameGrab(channel)
        val frame = grap.frame
        val coverFile = file.parentFile.resolve("${file.nameWithoutExtension}_cover.jpg")
        if (coverFile.exists()) coverFile.delete()
        ImageIO.write(frame, "jpg", coverFile)
        addImageProperty(tsBuilder, coverFile)

        tsBuilder.addProperty(
            PropertySpec.builder(file.nameWithoutExtension, StaticVideo::class)
                .addAnnotation(Transient::class)
                .initializer(
                    CodeBlock.of(
                    """
                        newVideo(
                            fileName = %S,
                            width = ${frame.width},
                            height = ${frame.height},
                            duration = ${(grap.videoTrack.meta.totalDuration * 1000).toLong()},
                            cover = ${coverFile.nameWithoutExtension}.url,
                        )
                    """.trimIndent(),
                    "${file.name}",
                ))
                .build()
        )
        channel.close()
    }

    private fun getContentCodeBlock(): String {
        val sb = StringBuilder()
        content.forEachIndexed { i, s ->
            if (i == content.lastIndex) {
                sb.append(s).append(',')
            } else {
                sb.append(s).appendLine(',')
            }
        }
        return sb.toString()
    }
}

fun <T : StaticAlbum> T.runScript() {
    StaticAlbumScript(this).run()
}