package zhupff.gadget.basic.file

import zhupff.gadget.basic.IS_DEBUG
import java.io.File


val STATIC_RES_DIR: File by lazy {
    Thread.currentThread().contextClassLoader.getResource("static")!!.path.let { path ->
        if (path.isNullOrEmpty()) {
            throw IllegalStateException("resources/static/ path is null")
        }
        File(path)
    }.also { file ->
        if (!file.exists()) {
            throw IllegalStateException("resources/static/ dir not exists")
        }
    }
}

val RES_DIR: File = if (IS_DEBUG) {
    File("").resolve("database/d-res/src/main/resources/static").also { dir ->
        if (!dir.exists()) {
            throw IllegalStateException("RES_ORIGIN_DIR(${dir.absolutePath}) not exists")
        }
    }
} else {
    // TODO
    throw RuntimeException("Not implemented yet")
}

val CONFIG_DIR = RES_DIR.resolve("config").also { dir ->
    if (!dir.exists()) dir.mkdirs()
}

val TAGS_JSON_FILE = CONFIG_DIR.resolve("tags.json").also { file ->
    if (!file.exists()) {
        file.createNewFile()
    }
}

val USERS_JSON_FILE = CONFIG_DIR.resolve("users.json").also { file ->
    if (!file.exists()) {
        file.createNewFile()
    }
}


val IMAGE_SUFFIX = arrayOf(
    "jfif", "png", "jpg", "jpeg",
)

fun File.isImage(): Boolean {
    return extension in IMAGE_SUFFIX
}

val VIDEO_SUFFIX = arrayOf(
    "mp4",
)

fun File.isVideo(): Boolean {
    return extension in VIDEO_SUFFIX
}