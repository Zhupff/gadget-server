package zhupff.gadget.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.context.WebServerInitializedEvent
import org.springframework.context.ApplicationListener
import java.net.InetAddress

fun main(args: Array<String>) {
    runApplication<GadgetServerApplication>(*args)
}

@SpringBootApplication
class GadgetServerApplication : ApplicationListener<WebServerInitializedEvent> {
    companion object {
        @JvmStatic
        fun run(args: Array<String> = emptyArray()) {
            main(args)
        }
    }

    override fun onApplicationEvent(event: WebServerInitializedEvent) {
        println("webserver initialized")
        val ip = InetAddress.getLocalHost().hostAddress
        val port = event.applicationContext.webServer.port
        println("ip=$ip, port=$port")
    }
}