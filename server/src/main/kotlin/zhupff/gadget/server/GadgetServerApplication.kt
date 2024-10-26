package zhupff.gadget.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.context.WebServerInitializedEvent
import org.springframework.context.ApplicationListener
import java.net.InetAddress

internal fun main(args: Array<String>) {
    runApplication<GadgetServerApplication>(*args)
}



@SpringBootApplication
class GadgetServerApplication : ServerApi, ApplicationListener<WebServerInitializedEvent> {
    companion object : Runnable {
        override fun run() {
            main(emptyArray())
        }
    }

    init {
        ServerApi.compareAndSet(null, this)
    }

    override fun onApplicationEvent(event: WebServerInitializedEvent) {
        val ip = InetAddress.getLocalHost().hostAddress
        val port = event.applicationContext.webServer.port
    }

    override fun getTestStr(): String = "Welcome to Gadget Server"
}