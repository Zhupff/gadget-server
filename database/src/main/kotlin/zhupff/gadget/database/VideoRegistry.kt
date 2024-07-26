package zhupff.gadget.database

import java.util.concurrent.atomic.AtomicLong

object VideoRegistry {

    private val VIDEO_ID = AtomicLong(Int.MAX_VALUE.toLong())

    fun generateTagId(id: String? = null): String = "video_${if (id.isNullOrEmpty()) VIDEO_ID.incrementAndGet() else id}"
}