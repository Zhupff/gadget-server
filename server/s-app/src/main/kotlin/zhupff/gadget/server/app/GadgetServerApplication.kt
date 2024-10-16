package zhupff.gadget.server.app

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.context.WebServerInitializedEvent
import org.springframework.context.ApplicationListener
import zhupff.gadget.server.ServerApi
import java.net.InetAddress

@SpringBootApplication
class GadgetServerApplication : ApplicationListener<WebServerInitializedEvent> {
    companion object {
        fun prepare() {
            GlobalScope.launch {
                if (ServerApi.compareAndSet(false, true)) {
                    ServerApi
                    println("ServerApi prepared in ${Thread.currentThread()}")
                }
            }
        }

        @JvmStatic
        fun run(args: Array<String> = emptyArray()) {
            runApplication<GadgetServerApplication>(*args)
        }
    }

    override fun onApplicationEvent(event: WebServerInitializedEvent) {
        val ip = InetAddress.getLocalHost().hostAddress
        val port = event.applicationContext.webServer.port
        ServerApi.onAddressChanged(ip, port)
    }
}