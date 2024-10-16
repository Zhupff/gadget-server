package zhupff.gadget.database.model.statics

import zhupff.gadget.database.model.Album
import zhupff.gadget.database.model.Tag
import zhupff.gadget.database.model.User
import java.util.ArrayList
import java.util.HashMap
import java.util.ServiceLoader
import java.util.concurrent.atomic.AtomicLong

open class StaticAlbum(
    name: String,
    users: Array<User>,
    tags: Array<Tag>,
) : Album(
    "album_${ID.incrementAndGet()}",
    name,
    users,
    tags,
) {

    companion object : MutableList<StaticAlbum> by ArrayList() {
        private val ID = AtomicLong(Int.MAX_VALUE.toLong())
        private val MAP = HashMap<String, Int>()

        init {
            ServiceLoader.load(Album::class.java).forEach { album ->
                // do nothing
            }
        }

        operator fun get(key: String): StaticAlbum? {
            val index = MAP[key]
            if (index != null && index >= 0) {
                return StaticAlbum[index]
            }
            return null
        }
    }

    init {
        synchronized(StaticAlbum) {
            StaticAlbum.add(this)
            val index = StaticAlbum.lastIndex
            MAP[id] = index
            MAP[name] = index
        }
    }
}