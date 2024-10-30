package zhupff.gadget.server.model

import java.io.Serializable
import java.util.ServiceLoader
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

open class Tag(
    val id: String,
    val title: String,
    val link: String,
) : Serializable {

    companion object {
        private val init = AtomicBoolean(false)
        private val ID = AtomicLong(Short.MAX_VALUE.toLong())

        fun init() {
            if (init.compareAndSet(false, true)) {
                ServiceLoader.load(Tag::class.java).forEach {
                    // do nothing
                }
            }
        }

        fun newId(id: Long = ID.getAndIncrement()): String = "tag_$id"
    }
}