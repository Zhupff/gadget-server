package zhupff.gadget.database.model

import java.util.concurrent.atomic.AtomicInteger

open class User(
    val id: String,
    val name: String,
    val avatar: String,
) {

    companion object {
        private val ID = AtomicInteger(0)

        fun newID(number: Number = ID.incrementAndGet()): String = "user_${number}"

        val UNKNOWN = User(
            id = newID(0),
            name = "UNKNOWN",
            avatar = "avatar/unknown.png"
        )
    }
}