package zhupff.gadget.database

import zhupff.gadget.model.Tag
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicLong

object TagRegistry : MutableList<Tag> by CopyOnWriteArrayList() {

    private val TAG_ID = AtomicLong(Int.MAX_VALUE.toLong())

    init {
        ServiceLoader.load(Tag::class.java).toList().also {
            addAll(it)
        }
    }

    fun generateTagId(id: String? = null): String = "tag_${if (id.isNullOrEmpty()) TAG_ID.incrementAndGet() else id}"
}