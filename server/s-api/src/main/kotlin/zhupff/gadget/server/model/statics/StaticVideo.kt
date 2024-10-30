package zhupff.gadget.server.model.statics

import zhupff.gadget.server.basic.StaticDB
import zhupff.gadget.server.basic.StaticDBImpl
import zhupff.gadget.server.model.Tag
import zhupff.gadget.server.model.User
import zhupff.gadget.server.model.Video

open class StaticVideo(
    val staticID: Int = -1,
    title: String,
    width: Int,
    height: Int,
    duration: Long,
    cover: String,
    url: String,
    user: User,
    albumID: String? = null,
    tags: Array<Tag>
) : Video(
    if (staticID >= 0) {
        newId(Int.MAX_VALUE.toLong() + staticID)
    } else newId(),
    title,
    width,
    height,
    duration,
    cover,
    url,
    user,
    albumID,
    tags,
), StaticItem {

    companion object : StaticDB<StaticVideo> by StaticDBImpl() {
    }

    init {
        if (staticID >= 0 && !addStaticID(staticID)) {
            throw IllegalArgumentException("Video(${title})'s id is duplicated!")
        }
        addItem(this)
    }

    override fun getUniqueID(): String = this.id
}