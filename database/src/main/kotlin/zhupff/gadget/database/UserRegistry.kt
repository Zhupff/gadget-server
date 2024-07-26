package zhupff.gadget.database

import com.google.auto.service.AutoService
import zhupff.gadget.model.User
import java.util.ServiceLoader
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicLong

object UserRegistry : MutableList<User> by CopyOnWriteArrayList() {

    private val USER_ID = AtomicLong(Int.MAX_VALUE.toLong())

    init {
        ServiceLoader.load(User::class.java).toList().also {
            addAll(it)
        }
    }

    fun generateUserId(id: String? = null): String = "user_${if (id.isNullOrEmpty()) USER_ID.incrementAndGet() else id}"
}


@AutoService(User::class)
class User_Nobody : User(
    UserRegistry.generateUserId("0"),
    "Nobody",
    "/avatar/nobody.png",
)