package zhupff.gadget

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GadgetServerApplication {
    companion object {
        @JvmStatic
        fun run(args: Array<String> = emptyArray()) {
            main(args)
        }
    }
}

fun main(args: Array<String>) {
    runApplication<GadgetServerApplication>(*args)
}