package zhupff.gadget.server.model.statics

import zhupff.gadget.server.basic.StaticDB
import zhupff.gadget.server.basic.StaticDBImpl
import zhupff.gadget.server.model.Album
import zhupff.gadget.server.model.Tag
import zhupff.gadget.server.model.User

open class StaticAlbum(
    val staticID: Int = -1,
    title: String,
    user: User,
    tags: Array<Tag>,
    val path: String,
) : Album(
    if (staticID >= 0) {
        newId(Int.MAX_VALUE.toLong() + 1)
    } else newId(),
    title,
    user,
    tags,
), StaticItem {

    companion object : StaticDB<StaticAlbum> by StaticDBImpl() {
    }

    init {
        if (staticID >= 0 && !addStaticID(staticID)) {
            throw IllegalArgumentException("Album(${title})'s id is duplicated!")
        }
        addItem(this)
    }

    override fun getUniqueID(): String = this.id



    fun newImage(
        staticID: Int,
        file: String,
        width: Int,
        height: Int,
    ): StaticImage {
        return StaticImage(
            staticID = this.staticID * 1_000 + staticID,
            title = "${this.title}[${file.substringBeforeLast('.').replace('_', ' ')}]",
            width = width,
            height = height,
            url = "${this.path}${file}",
            user = this.user,
            albumID = this.id,
            tags = this.tags,
        )
    }

    fun newVideo(
        staticID: Int,
        file: String,
        width: Int,
        height: Int,
        duration: Long,
    ): StaticVideo {
        return StaticVideo(
            staticID = this.staticID * 1_000 + staticID,
            title = "${this.title}[${file.substringBeforeLast('.').replace('_', ' ')}]",
            width = width,
            height = height,
            duration = duration,
            cover = "${this.path}${file.substringBeforeLast('.')}.cover.jpeg",
            url = "${this.path}${file}",
            user = this.user,
            albumID = this.id,
            tags = this.tags,
        )
    }

    fun contents(block: StaticAlbum.(StaticAlbum) -> Array<Any>) {
        val contents = this.block(this)
        this.content = contents
    }
}