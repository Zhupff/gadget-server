package zhupff.gadget.server.model.statics

import zhupff.gadget.server.basic.StaticDB
import zhupff.gadget.server.basic.StaticDBImpl
import zhupff.gadget.server.model.Tag

open class StaticTag(
    val staticID: Int = -1,
    title: String,
    link: String,
) : Tag(
    if (staticID >= 0) {
        newId(Int.MAX_VALUE.toLong() + staticID)
    } else newId(),
    title,
    link,
), StaticItem {

    companion object : StaticDB<StaticTag> by StaticDBImpl() {
    }

    init {
        if (staticID >= 0 && !addStaticID(staticID)) {
            throw IllegalArgumentException("Tag(${title})'s id is duplicated!")
        }
        addItem(this)
    }

    override fun getUniqueID(): String = this.id
}