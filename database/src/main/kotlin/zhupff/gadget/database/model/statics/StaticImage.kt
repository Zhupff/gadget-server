package zhupff.gadget.database.model.statics

import zhupff.gadget.database.model.Image
import zhupff.gadget.database.model.Tag
import zhupff.gadget.database.model.User
import java.util.ArrayList
import java.util.HashMap
import java.util.ServiceLoader
import java.util.concurrent.atomic.AtomicLong

open class StaticImage(
    name: String,
    width: Int,
    height: Int,
    url: String,
    users: Array<User>,
    tags: Array<Tag>,
    albumId: String
) : Image(
    "image_${ID.incrementAndGet()}",
    name,
    width,
    height,
    url,
    users,
    tags,
    albumId,
) {

    companion object : MutableList<StaticImage> by ArrayList() {
        private val ID = AtomicLong(Int.MAX_VALUE.toLong())
        private val MAP = HashMap<String, Int>()

        init {
            ServiceLoader.load(Image::class.java).forEach { image ->
                // do nothing
            }
        }

        operator fun get(key: String): StaticImage? {
            val index = MAP[key]
            if (index != null && index >= 0) {
                return StaticImage[index]
            }
            return null
        }
    }

    init {
        synchronized(StaticImage) {
            StaticImage.add(this)
            val index = StaticImage.lastIndex
            MAP[id] = index
            MAP[name] = index
        }
    }
}