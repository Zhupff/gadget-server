package zhupff.gadget.database

import java.util.concurrent.atomic.AtomicLong

object ImageRegistry {

    private val IMAGE_ID = AtomicLong(Int.MAX_VALUE.toLong())

    fun generateTagId(id: String? = null): String = "image_${if (id.isNullOrEmpty()) IMAGE_ID.incrementAndGet() else id}"
}