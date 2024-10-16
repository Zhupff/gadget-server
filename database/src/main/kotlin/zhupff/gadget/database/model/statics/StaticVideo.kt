package zhupff.gadget.database.model.statics

import zhupff.gadget.database.model.Tag
import zhupff.gadget.database.model.User
import zhupff.gadget.database.model.Video
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
    companion object : MutableMap<String, StaticVideo> by HashMap() {
        private val ID = AtomicLong(Int.MAX_VALUE.toLong())

        init {
            ServiceLoader.load(Video::class.java).forEach { video ->
//                if (video is StaticVideo) {
//                    StaticVideo[video.id] = video
//                    StaticVideo[video.name] = video
//                }
            }
        }
    }

    init {
        StaticVideo[id] = this
        StaticVideo[name] = this
    }
}