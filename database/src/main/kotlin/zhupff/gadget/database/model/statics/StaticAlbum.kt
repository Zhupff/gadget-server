package zhupff.gadget.database.model.statics

import zhupff.gadget.database.model.Album
import zhupff.gadget.database.model.Tag
import zhupff.gadget.database.model.User
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

    companion object : MutableMap<String, StaticAlbum> by HashMap() {
        private val ID = AtomicLong(Int.MAX_VALUE.toLong())

        init {
            ServiceLoader.load(Album::class.java).forEach { album ->
//                if (album is StaticAlbum) {
//                    StaticAlbum[album.id] = album
//                    StaticAlbum[album.name] = album
//                }
            }
        }
    }

    init {
        StaticAlbum[id] = this
        StaticAlbum[name] = this
    }
}