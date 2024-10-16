package zhupff.gadget.database.model.statics

import zhupff.gadget.database.model.Tag
import java.util.ServiceLoader
import java.util.concurrent.atomic.AtomicLong

open class StaticTag(
    name: String,
    link: String,
) : Tag(
    "tag_${ID.incrementAndGet()}",
    name,
    link,
) {
    companion object : MutableMap<String, StaticTag> by HashMap() {
        private val ID = AtomicLong(Int.MAX_VALUE.toLong())

        init {
            ServiceLoader.load(Tag::class.java).forEach { tag ->
//                if (tag is StaticTag) {
//                    StaticTag[tag.id] = tag
//                    StaticTag[tag.name] = tag
//                }
            }
        }
    }

    init {
        StaticTag[id] = this
        StaticTag[name] = this
    }
}