package zhupff.gadget.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.context.WebServerInitializedEvent
import org.springframework.context.ApplicationListener
import java.net.InetAddress

@SpringBootApplication
class GadgetServerApplication : ApplicationListener<WebServerInitializedEvent> {
    companion object {
        @JvmStatic
        fun run(args: Array<String> = emptyArray()) {
            runApplication<GadgetServerApplication>(*args)
        }
    }

    override fun onApplicationEvent(event: WebServerInitializedEvent) {
        println("webserver initialized")
        val ip = InetAddress.getLocalHost().hostAddress
        val port = event.applicationContext.webServer.port
        ServerApi.onBaseUrlChanged(ip, port)
    }
}