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