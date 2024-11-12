package zhupff.gadget.server.res.script

import org.jcodec.api.awt.AWTFrameGrab
import org.jcodec.common.io.NIOUtils
import org.springframework.util.DigestUtils
import org.springframework.util.FileCopyUtils
import zhupff.gadget.server.basic.json.gson
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileNotFoundException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import javax.imageio.ImageIO

private fun main() {
    AlbumScript().run()
}

class AlbumScript : Runnable {

    private val fromDir = ORIGIN_RES.resolve("album").also {
        if (!it.exists() || !it.isDirectory) {
            throw FileNotFoundException("${it.absolutePath} does not exists!")
        }
    }

    private val toDir = LOCAL_RES.resolve(fromDir.name).also {
        if (!it.exists()) {
            it.mkdirs()
        }
        if (!it.isDirectory) {
            throw FileNotFoundException("${it.absolutePath} is not a directory!")
        }
    }

    private val executor by lazy { Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2) }

    private val lock = Any()

    private val authorRegex = Regex("\\{([^\\{\\}])+\\}")
    private val tagRegex = Regex("\\[([^\\[\\]])+\\]")

    private fun process(albumDir: File) {
        val album = A()
        val toAlbumDir = createToAlbumDir(albumDir, album) ?: return
        try {
            albumDir.listFiles()!!.sortedBy {
                it.nameWithoutExtension
            }.forEach { file ->
                if (file.isImage) {
                    processImage(file, toAlbumDir, album)
                } else if (file.isVideo) {
                    processVideo(file, toAlbumDir, album)
                }
            }
            val infoJsonFile = toAlbumDir.resolve("info.json").also {
                it.createNewFile()
            }
            infoJsonFile.writeText(gson.toJson(album))
        } catch (e: Exception) {
            e.printStackTrace()
            toAlbumDir.deleteRecursively()
            return
        }
        albumDir.deleteRecursively()
    }

    private fun createToAlbumDir(albumDir: File, album: A): File? {
        synchronized(lock) {
            val ts = System.currentTimeMillis()
            val albumDirName = albumDir.name
            var albumName = albumDirName
            authorRegex.findAll(albumDirName)
                .onEach { albumName = albumName.replace(it.value, "") }
                .map { it.value.substring(1, it.value.length - 1) }
                .let { album.authors.addAll(it) }
            tagRegex.findAll(albumDirName)
                .onEach { albumName = albumName.replace(it.value, "") }
                .map { it.value.substring(1, it.value.length - 1) }
                .let { album.tags.addAll(it) }
            album.name = albumName
            album.id = "[album]${ts}"

            val toAlbumDirName = album.authors.firstOrNull()?.let {
                "${it}_${ts}"
            } ?: ts.toString()
            val toAlbumDir = toDir.resolve(toAlbumDirName)
            if (toAlbumDir.exists()) {
                println("${toAlbumDir.absolutePath} already exists!")
                return null
            }
            toAlbumDir.mkdirs()
            return toAlbumDir
        }
    }

    private fun processImage(file: File, toAlbum: File, album: A?): File {
        val fromImage = ImageIO.read(file)
        val scaledSize = scale(fromImage.width, fromImage.height, 1920, 1080)
        val scaledImage = fromImage.getScaledInstance(scaledSize.first, scaledSize.second, Image.SCALE_DEFAULT)
        val toImage = BufferedImage(scaledSize.first, scaledSize.second, BufferedImage.TYPE_INT_RGB)
        toImage.graphics.let { graphics ->
            graphics.drawImage(scaledImage, 0, 0, null)
            graphics.dispose()
        }
        val toFile = toAlbum.resolve(file.nameWithoutExtension)
        ImageIO.write(toImage, "JPEG", toFile)
        val md5 = toFile.inputStream().use {
            DigestUtils.md5DigestAsHex(it)
        }
        val renameFile = toAlbum.resolve(md5 + ".jpeg")
        toFile.renameTo(renameFile)

        album?.contents?.add(I(
            id = "[image]${md5}",
            width = scaledSize.first,
            height = scaledSize.second,
            url = renameFile.absolutePath.replace(LOCAL_RES.absolutePath, "").replace("\\", "/")
        ))

        return renameFile
    }

    private fun processVideo(file: File, toAlbum: File, album: A?): File {
        val md5 = file.inputStream().use {
            DigestUtils.md5DigestAsHex(it)
        }
        val toFile = toAlbum.resolve(md5 + "." + file.extension)
        FileCopyUtils.copy(file, toFile)

        val channel = NIOUtils.readableChannel(toFile)
        val grap = AWTFrameGrab.createAWTFrameGrab(channel)
        val frame = grap.frame
        val coverTempFile = toAlbum.resolve(md5 + "_cover")
        ImageIO.write(frame, "JPEG", coverTempFile)
        val coverFile = processImage(coverTempFile, toAlbum, null)

        album?.contents?.add(V(
            id = "[video]${md5}",
            width = frame.width,
            height = frame.height,
            duration = (grap.videoTrack.meta.totalDuration * 1000).toLong(),
            cover = coverFile.absolutePath.replace(LOCAL_RES.absolutePath, "").replace("\\", "/"),
            url = toFile.absolutePath.replace(LOCAL_RES.absolutePath, "").replace("\\", "/")
        ))

        channel.close()
        coverTempFile.deleteOnExit()
        return toFile
    }

    override fun run() {
        val albums = fromDir.listFiles()?.filter {
            it.isDirectory && !it.listFiles().isNullOrEmpty()
        } ?: return
        val latch = CountDownLatch(albums.size)

        albums.forEach { album ->
            executor.execute {
                process(album)
                latch.countDown()
                println("latch: ${latch.count}")
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

    private class A {
        lateinit var id: String
        lateinit var name: String
        val authors: ArrayList<String> = ArrayList()
        val tags: ArrayList<String> = ArrayList()
        val contents: ArrayList<Any> = ArrayList()
    }

    private class I(
        val id: String,
        val width: Int,
        val height: Int,
        val url: String,
    )

    private class V(
        val id: String,
        val width: Int,
        val height: Int,
        val duration: Long,
        val cover: String,
        val url: String,
    )
}