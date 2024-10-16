package zhupff.gadget.database.op

import zhupff.gadget.basic.logger.printD
import zhupff.gadget.basic.logger.printlnI
import zhupff.gadget.basic.logger.printlnV
import zhupff.gadget.basic.logger.printlnW
import java.util.*


fun main() {
    TagDB
    Main.run()
}

private object Main : Runnable {

    override fun run() {
        val inputScanner = Scanner(System.`in`)
        while (configOp(inputScanner)) { }
    }

    private fun configOp(inputScanner: Scanner): Boolean {
        printlnI("\n[1] Config Operation")
        OpWithCode.values().forEach {
            printlnV("Input ${it.ordinal} to ${it.actionDescribe};")
        }
        printD("Input please:")
        val code = inputScanner.nextInt()
        val op = OpWithCode.values().find { it.ordinal == code }

        when (op) {
            OpWithCode.EXIT -> return false
            OpWithCode.TAG -> while (TagDB.opWithCode(inputScanner)) {}
            OpWithCode.USERS -> return true
            else -> {
                printlnW("Input wrong, please input again!")
                return true
            }
        }

        return true
    }
}


private enum class OpWithCode(
    val actionDescribe: String,
) {
    EXIT("Exit Program"),
    TAG("Config tags.json File"),
    USERS("Config users.json File"),
}