package zhupff.gadget.server.model.statics

import zhupff.gadget.server.basic.StaticDB
import zhupff.gadget.server.basic.StaticDBImpl
import zhupff.gadget.server.model.Image
import zhupff.gadget.server.model.Tag
import zhupff.gadget.server.model.User

open class StaticImage(
    val staticID: Int = -1,
    title: String,
    width: Int,
    height: Int,
    url: String,
    user: User,
    albumID: String? = null,
    tags: Array<Tag>,
) : Image(
    if (staticID >= 0) {
        newId(Int.MAX_VALUE.toLong() + staticID)
    } else newId(),
    title,
    width,
    height,
    url,
    user,
    albumID,
    tags,
), StaticItem {

    companion object : StaticDB<StaticImage> by StaticDBImpl() {
    }

    init {
        if (staticID >= 0 && !addStaticID(staticID)) {
            throw IllegalArgumentException("Image(${title})'s id is duplicated!")
        }
        addItem(this)
    }

    override fun getUniqueID(): String = this.id
}