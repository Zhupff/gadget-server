package zhupff.gadget.database.model.statics

import zhupff.gadget.database.model.Image
import zhupff.gadget.database.model.Tag
import zhupff.gadget.database.model.User
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

    companion object : MutableMap<String, StaticImage> by HashMap() {
        private val ID = AtomicLong(Int.MAX_VALUE.toLong())

        init {
            ServiceLoader.load(Image::class.java).forEach { image ->
                if (image is StaticImage) {
                    StaticImage[image.id] = image
                    StaticImage[image.name] = image
                }
            }
        }
    }

    init {
        StaticImage[id] = this
        StaticImage[name] = this
    }
}