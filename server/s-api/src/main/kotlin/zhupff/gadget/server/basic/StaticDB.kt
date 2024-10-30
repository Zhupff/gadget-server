package zhupff.gadget.server.basic

import zhupff.gadget.server.model.statics.StaticItem
import kotlin.collections.HashMap
import kotlin.collections.HashSet

interface StaticDB<T : StaticItem> {

    fun addStaticID(id: Int): Boolean

    fun addItem(obj: T)

    fun getItem(key: String): T?

    fun size(): Int

    fun toList(): List<T>
}

class StaticDBImpl<T: StaticItem> : StaticDB<T> {
    private val staticID = HashSet<Int>()
    private val staticData = HashMap<String, T>()

    override fun addStaticID(id: Int): Boolean {
        var contains: Boolean
        synchronized(staticID) {
            contains = staticID.add(id)
        }
        return contains
    }

    override fun addItem(obj: T) {
        synchronized(staticData) {
            staticData[obj.getUniqueID()] = obj
        }
    }

    override fun getItem(id: String): T? {
        var result: T?
        synchronized(staticData) {
            result = staticData[id]
        }
        return result
    }

    override fun size(): Int = staticData.size

    override fun toList(): List<T> = staticData.values.toList()
}