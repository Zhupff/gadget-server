package zhupff.gadget.database.model.statics

import zhupff.gadget.database.model.User
import java.util.ArrayList
import java.util.HashMap
import java.util.ServiceLoader
import java.util.concurrent.atomic.AtomicLong

open class StaticUser(
    name: String,
    avatar: String,
) : User(
    newID(ID.incrementAndGet()),
    name,
    avatar
) {
    companion object : MutableList<StaticUser> by ArrayList() {
        private val ID = AtomicLong(Int.MAX_VALUE.toLong())
        private val MAP = HashMap<String, Int>()

        init {
            ServiceLoader.load(User::class.java).forEach { user ->
                // do nothing
            }
        }

        operator fun get(key: String): StaticUser? {
            val index = MAP[key]
            if (index != null && index >= 0) {
                return StaticUser[index]
            }
            return null
        }
    }

    init {
        synchronized(StaticUser) {
            StaticUser.add(this)
            val index = StaticUser.lastIndex
            MAP[id] = index
            MAP[name] = index
        }
    }
}