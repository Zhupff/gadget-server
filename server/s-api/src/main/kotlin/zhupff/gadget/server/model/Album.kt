package zhupff.gadget.server.model

import java.io.Serializable
import java.util.ServiceLoader
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

open class Album(
    val id: String,
    val title: String,
    val user: User,
    val tags: Array<Tag>,
) : Serializable {

    companion object {
        private val init = AtomicBoolean(false)
        private val ID = AtomicLong(Short.MAX_VALUE.toLong())

        fun init() {
            if (init.compareAndSet(false, true)) {
                ServiceLoader.load(Album::class.java).forEach {
                    // do nothing
                }
            }
        }

        fun newId(id: Long = ID.getAndIncrement()): String = "album_$id"
    }

    var content: Array<out Any> = emptyArray()
}