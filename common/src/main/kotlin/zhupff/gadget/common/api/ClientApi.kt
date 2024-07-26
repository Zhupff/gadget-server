package zhupff.gadget.common.api

import java.util.ServiceLoader

interface ClientApi {
    companion object : ClientApi by ServiceLoader.load(ClientApi::class.java).first()
}