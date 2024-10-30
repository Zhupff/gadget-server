package zhupff.gadget.server.model.statics

import zhupff.gadget.server.basic.StaticDB
import zhupff.gadget.server.basic.StaticDBImpl
import zhupff.gadget.server.model.Tag
import zhupff.gadget.server.model.User

open class StaticUser(
    val staticID: Int = -1,
    name: String,
    avatar: String,
) : User(
    if (staticID >= 0) {
        newId(Int.MAX_VALUE.toLong() + staticID)
    } else newId(),
    name,
    avatar,
), StaticItem {

    companion object : StaticDB<StaticUser> by StaticDBImpl() {
    }

    init {
        if (staticID >= 0 && !addStaticID(staticID)) {
            throw IllegalArgumentException("User(${name})'s id is duplicated!")
        }
        addItem(this)
    }

    override fun getUniqueID(): String = this.id



    fun newAlbum(
        staticID: Int,
        path: String,
        tags: Array<Tag>,
    ): StaticAlbum {
        return StaticAlbum(
            staticID = this.staticID * 10_000 + staticID,
            title = path.substringAfterLast('/').replace('_', ' '),
            user = this,
            tags = tags,
            path = path,
        )
    }
}