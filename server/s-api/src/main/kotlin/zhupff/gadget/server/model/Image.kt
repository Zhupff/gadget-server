package zhupff.gadget.server.model

import java.io.Serializable
import java.util.ServiceLoader
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

open class Image(
    val id: String,
    val title: String,
    val width: Int,
    val height: Int,
    val url: String,
    val user: User,
    val albumID: String? = null,
    val tags: Array<Tag>,
) : Serializable {

    companion object {
        private val init = AtomicBoolean(false)
        private val ID = AtomicLong(Short.MAX_VALUE.toLong())

        fun init() {
            if (init.compareAndSet(false, true)) {
                ServiceLoader.load(Image::class.java).forEach {
                    // do nothing.
                }
            }
        }

        fun newId(id: Long = ID.getAndIncrement()): String = "image_$id"
    }
}