package zhupff.gadget.client

import java.util.concurrent.atomic.AtomicReference

interface ClientApi {
    companion object : AtomicReference<ClientApi>() {
    }

    fun getTestStr(): String
}