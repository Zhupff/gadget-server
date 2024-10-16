package zhupff.gadget.client

import androidx.compose.runtime.State
import java.util.ServiceLoader
import java.util.concurrent.atomic.AtomicBoolean

interface ClientApi {

    companion object : ClientApi by ServiceLoader.load(ClientApi::class.java).first(),
        AtomicBoolean(false)
    {
    }


    fun getAddressState(): State<String>
}