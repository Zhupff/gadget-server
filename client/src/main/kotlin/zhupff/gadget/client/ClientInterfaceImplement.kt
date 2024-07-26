package zhupff.gadget.client

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.auto.service.AutoService
import zhupff.gadget.common.api.ClientInterface
import zhupff.gadget.common.api.ServerInterface
import java.util.*

@AutoService(ClientInterface::class)
class ClientInterfaceImplement : ClientInterface by ClientApi

object ClientApi : ClientInterface {

    var baseUrl: String by mutableStateOf(""); private set

    override fun onBaseUrlChanged(ip: String, port: Int) {
        baseUrl = "http://${ip}:${port}"
    }
}

val ServerApi: ServerInterface? by lazy { ServiceLoader.load(ServerInterface::class.java).firstOrNull() }