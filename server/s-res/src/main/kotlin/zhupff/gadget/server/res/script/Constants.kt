package zhupff.gadget.server.res.script

import java.io.File
import java.io.FileNotFoundException

internal val ORIGIN_RES by lazy {
    File("").resolve("originres").also {
        println("originres: ${it.absolutePath}")
        if (!it.exists()) {
            throw FileNotFoundException("originres NOT FOUND!")
        }
    }
}

internal val LOCAL_RES by lazy {
    File("").resolve("localres").also {
        println("localres: ${it.absolutePath}")
        if (!it.exists()) {
            throw FileNotFoundException("localres NOT FOUND!")
        }
    }
}

internal val IMAGE_EXT by lazy {
    arrayOf("jfif", "jpeg", "jpg", "png")
}

internal val VIDEO_EXT by lazy {
    arrayOf("mp4", "m4v")
}

internal val File.isImage: Boolean; get() = extension in IMAGE_EXT
internal val File.isVideo: Boolean; get() = extension in VIDEO_EXT

internal fun scale(width: Int, height: Int, L: Int = 1920, S: Int = 1080): Pair<Int, Int> {
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