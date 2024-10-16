package zhupff.gadget.database.model.statics

import zhupff.gadget.database.model.Tag
import java.util.ArrayList
import java.util.HashMap
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
    companion object : MutableList<StaticTag> by ArrayList() {
        private val ID = AtomicLong(Int.MAX_VALUE.toLong())
        private val MAP = HashMap<String, Int>()

        init {
            ServiceLoader.load(Tag::class.java).forEach { tag ->
                // do nothing
            }
        }

        operator fun get(key: String): StaticTag? {
            val index = MAP[key]
            if (index != null && index >= 0) {
                return StaticTag[index]
            }
            return null
        }
    }

    init {
        synchronized(StaticTag) {
            StaticTag.add(this)
            val index = StaticTag.lastIndex
            MAP[id] = index
            MAP[name]  = index
        }
    }
}