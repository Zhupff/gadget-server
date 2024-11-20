package zhupff.gadget.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.context.WebServerInitializedEvent
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationListener
import java.net.InetAddress
import java.util.concurrent.atomic.AtomicReference

internal fun main(args: Array<String>) {
    println("args: ${args.toList()}")
    if (GadgetServerApplication.get() != null) {
        println("There is a GadgetServer instance running.")
        return
    }
    val context = runApplication<GadgetServerApplication>(*args)
    if (!GadgetServerApplication.compareAndSet(null, context)) {
        throw RuntimeException("Can not run multiple server at the same time.")
    }
}



@SpringBootApplication
class GadgetServerApplication : ServerApi, ApplicationListener<WebServerInitializedEvent> {
    companion object : AtomicReference<ApplicationContext>(), Runnable {

        val isRunning: Boolean; get() = get() != null

        override fun run() {
            main(emptyArray())
        }

        fun exit() {
            val context = GadgetServerApplication.getAndSet(null) ?: return
            SpringApplication.exit(context)
            println("GadgetServer exit!")
        }
    }

    init {
        ServerApi.compareAndSet(null, this)
    }

    override fun onApplicationEvent(event: WebServerInitializedEvent) {
        val ip = InetAddress.getLocalHost().hostAddress
        val port = event.applicationContext.webServer.port
        println("ip:port --> ${ip}:${port}")
    }

    override fun getTestStr(): String = "Welcome to Gadget Server"
}