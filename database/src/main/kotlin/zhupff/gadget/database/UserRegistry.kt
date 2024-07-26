package zhupff.gadget.database

import zhupff.gadget.common.model.User
import java.util.concurrent.atomic.AtomicLong

object UserRegistry {

    private val USER_ID = AtomicLong(Int.MAX_VALUE.toLong())

    fun generateUserId(id: String? = null): String = "user_${if (id.isNullOrEmpty()) USER_ID.incrementAndGet() else id}"
}


object User_Nobody : User(
    UserRegistry.generateUserId("0"),
    "Nobody",
    "/avatar/nobody.png",
)