package zhupff.gadget.database.model.statics

import zhupff.gadget.database.model.User
import java.util.ServiceLoader
import java.util.concurrent.atomic.AtomicLong

open class StaticUser(
    name: String,
    avatar: String,
) : User(
    "user_${ID.incrementAndGet()}",
    name,
    avatar
) {
    companion object : MutableMap<String, StaticUser> by HashMap() {
        private val ID = AtomicLong(Int.MAX_VALUE.toLong())

        init {
            ServiceLoader.load(User::class.java).forEach { user ->
//                if (user is StaticUser) {
//                    StaticUser[user.id] = user
//                    StaticUser[user.name] = user
//                }
            }
        }
    }

    init {
        StaticUser[id] = this
        StaticUser[name] = this
    }
}