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

    @Transient
    open val path: String = ""

    init {
        synchronized(StaticAlbum) {
            StaticAlbum.add(this)
            val index = StaticAlbum.lastIndex
            MAP[id] = index
            MAP[name] = index
        }
    }


    fun newImage(
        fileName: String,
        width: Int,
        height: Int,
    ) = StaticImage(
        name = "${this.name}[${fileName.substringBeforeLast('.').replace('_', ' ')}]",
        width = width,
        height = height,
        url = "${this.path}$fileName",
        users = this.users,
        tags = this.tags,
        albumId = this.id,
    )

    fun newVideo(
        fileName: String,
        width: Int,
        height: Int,
        duration: Long,
        cover: String,
    ) = StaticVideo(
        name = "${this.name}[${fileName.substringBeforeLast('.').replace('_', ' ')}]",
        width = width,
        height = height,
        duration = duration,
        cover = cover,
        url = "${this.path}$fileName",
        users = this.users,
        tags = this.tags,
        albumId = this.id,
    )
}