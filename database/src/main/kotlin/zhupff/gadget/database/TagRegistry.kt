package zhupff.gadget.database

import java.util.concurrent.atomic.AtomicLong

object TagRegistry {

    private val TAG_ID = AtomicLong(Int.MAX_VALUE.toLong())

    fun generateTagId(id: String? = null): String = "tag_${if (id.isNullOrEmpty()) TAG_ID.incrementAndGet() else id}"
}