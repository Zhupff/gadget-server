package zhupff.gadget.basic.logger

import kotlin.reflect.KClass

object Logger {

    private const val ESC = '\u001B'
    private const val CSI_RESET = "$ESC[0m"

    enum class Level(val csi: String) {
        V("$ESC[90m"),
        D("$ESC[32m"),
        I("$ESC[34m"),
        W("$ESC[33m"),
        E("$ESC[31m"),
    }

    fun log(level: Level, tag: Any, content: Any?): Any {
        val tagString = when (tag) {
            is KClass<*> -> tag.java.simpleName
            is Class<*> -> tag.simpleName
            is CharSequence -> tag.toString()
            else -> "${tag::class.java.simpleName}(${hashCode()})"
        }
        val contentString = when (content) {
            is Throwable -> content.stackTraceToString()
            else -> content.toString()
        }
        println("${level.csi}${tagString}: ${contentString}$CSI_RESET")
        return tag
    }

    fun print(level: Level, content: Any?) {
        val contentString = when (content) {
            is Throwable -> content.stackTraceToString()
            else -> content.toString()
        }
        print("${level.csi}${contentString}$CSI_RESET")
    }

    fun println(level: Level, content: Any?) {
        val contentString = when (content) {
            is Throwable -> content.stackTraceToString()
            else -> content.toString()
        }
        println("${level.csi}${contentString}$CSI_RESET")
    }
}

fun Any.logV(content: Any?) = apply { Logger.log(Logger.Level.V, this, content) }
fun Any.logD(content: Any?) = apply { Logger.log(Logger.Level.D, this, content) }
fun Any.logI(content: Any?) = apply { Logger.log(Logger.Level.I, this, content) }
fun Any.logW(content: Any?) = apply { Logger.log(Logger.Level.W, this, content) }
fun Any.logE(content: Any?) = apply { Logger.log(Logger.Level.E, this, content) }

fun printV(content: Any?) {
    Logger.println(Logger.Level.V, content)
}
fun printD(content: Any?) {
    Logger.print(Logger.Level.D, content)
}
fun printI(content: Any?) {
    Logger.print(Logger.Level.I, content)
}
fun printW(content: Any?) {
    Logger.print(Logger.Level.W, content)
}
fun printE(content: Any?) {
    Logger.print(Logger.Level.E, content)
}

fun printlnV(content: Any?) {
    Logger.println(Logger.Level.V, content)
}
fun printlnD(content: Any?) {
    Logger.println(Logger.Level.D, content)
}
fun printlnI(content: Any?) {
    Logger.println(Logger.Level.I, content)
}
fun printlnW(content: Any?) {
    Logger.println(Logger.Level.W, content)
}
fun printlnE(content: Any?) {
    Logger.println(Logger.Level.E, content)
}