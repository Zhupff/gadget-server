package zhupff.gadget.server

import com.google.auto.service.AutoService
import zhupff.gadget.common.api.ClientInterface
import zhupff.gadget.common.api.ServerInterface
import java.util.*

@AutoService(ServerInterface::class)
class ServerInterfaceImplement : ServerInterface by ServerApi

object ServerApi : ServerInterface {

    var ip: String = ""; private set

    var port: Int = 0; private set

    fun onBaseUrlChanged(newIp: String, newPort: Int) {
        var changed = false
        if (this.ip != newIp) {
            changed = true
            this.ip = newIp
        }
        if (this.port != newPort) {
            changed = true
            this.port = newPort
        }
        if (changed) {
            println("ServerApi: onBaseUrlChanged($newIp, $newPort)")
            ClientApi?.onBaseUrlChanged(newIp, newPort)
        }
    }
}

val ClientApi: ClientInterface? by lazy { ServiceLoader.load(ClientInterface::class.java).firstOrNull() }