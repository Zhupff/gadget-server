package zhupff.gadget.database.model.statics

import zhupff.gadget.database.model.Tag
import zhupff.gadget.database.model.User
import zhupff.gadget.database.model.Video
import java.util.ArrayList
import java.util.HashMap
import java.util.ServiceLoader
import java.util.concurrent.atomic.AtomicLong

open class StaticVideo(
    name: String,
    width: Int,
    height: Int,
    duration: Long,
    cover: String,
    url: String,
    users: Array<User>,
    tags: Array<Tag>,
    albumId: String,
) : Video(
    "video_${ID.incrementAndGet()}",
    name,
    width,
    height,
    duration,
    cover,
    url,
    users,
    tags,
    albumId,
) {
    companion object : MutableList<StaticVideo> by ArrayList() {
        private val ID = AtomicLong(Int.MAX_VALUE.toLong())
        private val MAP = HashMap<String, Int>()

        init {
            ServiceLoader.load(Video::class.java).forEach { video ->
                // do nothing
            }
        }

        operator fun get(key: String): StaticVideo? {
            val index = MAP[key]
            if (index != null && index >= 0) {
                return StaticVideo[index]
            }
            return null
        }
    }

    init {
        synchronized(StaticVideo) {
            StaticVideo.add(this)
            val index = StaticVideo.lastIndex
            MAP[id] = index
            MAP[name] = index
        }
    }
}