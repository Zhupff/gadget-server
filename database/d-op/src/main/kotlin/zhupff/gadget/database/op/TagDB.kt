package zhupff.gadget.database.op

import org.json.JSONArray
import zhupff.gadget.basic.file.TAGS_JSON_FILE
import zhupff.gadget.basic.json.JsonUtil
import zhupff.gadget.basic.logger.*
import zhupff.gadget.database.model.Tag
import java.util.Scanner
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

object TagDB : MutableMap<String, Tag> by ConcurrentHashMap() {

    private val TAG_ID = AtomicLong(Int.MAX_VALUE.toLong())

    init {
        try {
            val jsonArray = JSONArray(TAGS_JSON_FILE.readText())
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val obj = JsonUtil.fromJson(jsonObject, Tag::class.java)
                if (obj != null) {
                    put(obj.id, obj)
                    put(obj.name, obj)
                }
            }
        } catch (e: Exception) {}
    }

    fun generateId(id: String? = null): String = "tag_${if (id.isNullOrEmpty()) TAG_ID.incrementAndGet() else id}"



    private enum class OpWithCode(
        val actionDescribe: String,
    ) {
        BACK("Go Back"),
        ADD("Add or Edit Item"),
        DEL("Del Item"),
        CHECK("Check Item"),
    }

    internal fun opWithCode(inputScanner: Scanner): Boolean {
        printlnI("\n[2] Config tags.json file!")
        OpWithCode.values().forEach {
            printlnV("Input ${it.ordinal} to ${it.actionDescribe};")
        }
        printD("Input please:")
        val code = inputScanner.nextInt()
        val op = OpWithCode.values().find { it.ordinal == code }
        when (op) {
            OpWithCode.BACK -> return false
            OpWithCode.ADD -> while (addItemWithOp(inputScanner)) {}
            else -> {
                printlnW("Input wrong, please input again!")
                return true
            }
        }
        writeToJsonFile()
        return true
    }

    internal fun addItemWithOp(inputScanner: Scanner): Boolean {
        printlnI("\n[3] Add or Edit a Tag!")
        printlnV("Input 0 whenever you want to cancel and go back.")

        printD("Input Tag's name:")
        val tagName = inputScanner.next().trim()
        if ("0".contentEquals(tagName, true)) {
            return false
        }
        val tag = TagDB[tagName]
        if (tag != null) {
            printlnW("Tag($tagName) already exit. If go on, you will edit it.")
        }

        printD("Input Tag's link (input null if it is):")
        var tagLink = inputScanner.next().trim()
        if ("0".contentEquals(tagLink, true)) {
            return false
        } else if ("null".contentEquals(tagLink, true)) {
            tagLink = ""
        }
        val newTag = Tag(
            id = tag?.id ?: generateId(),
            name = tagName,
            link = tagLink,
        )

        put(newTag.id, newTag)
        put(newTag.name, newTag)
        return true
    }

    private fun writeToJsonFile() {
        val json = JsonUtil.toJson(values.toSet())
        TAGS_JSON_FILE.writeText(json)
    }
}