package zhupff.gadget.common.api

import java.io.File


val RES_STATIC_DIR = Thread.currentThread().contextClassLoader.getResource("static")!!.path.let { path ->
    if (path.isNullOrEmpty()) {
        throw IllegalStateException("resources/static/ path is null")
    }
    File(path)
}.also { file ->
    if (!file.exists()) {
        throw IllegalStateException("resources/static/ dir not exists")
    }
}

object ResourceApi {
}