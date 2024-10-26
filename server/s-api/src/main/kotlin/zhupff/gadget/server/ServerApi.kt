package zhupff.gadget.server

import java.util.concurrent.atomic.AtomicReference

interface ServerApi {
    companion object : AtomicReference<ServerApi>() {
    }

    fun getTestStr(): String
}