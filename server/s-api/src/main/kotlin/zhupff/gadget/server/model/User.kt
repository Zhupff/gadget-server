package zhupff.gadget.server.model

import java.io.Serializable
import java.util.ServiceLoader
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

open class User(
    val id: String,
    val name: String,
    val avatar: String,
) : Serializable {

    companion object {
        private val init = AtomicBoolean(false)
        private val ID = AtomicLong(Short.MAX_VALUE.toLong())

        fun init() {
            if (init.compareAndSet(false, true)) {
                ServiceLoader.load(User::class.java).forEach {
                    // do nothing.
                }
            }
        }

        fun newId(id: Long = ID.getAndIncrement()): String = "user_$id"
    }
}