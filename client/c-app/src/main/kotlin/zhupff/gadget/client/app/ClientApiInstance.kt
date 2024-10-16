package zhupff.gadget.client.app

import androidx.compose.runtime.*
import com.google.auto.service.AutoService
import kotlinx.coroutines.*
import zhupff.gadget.client.ClientApi
import zhupff.gadget.server.ServerApi

@AutoService(ClientApi::class)
class ClientApiInstance : ClientApi,
    CoroutineScope by GlobalScope
{

    private val addressState: MutableState<String> = mutableStateOf("")

    init {
        launch {
            ServerApi.getAddress().collect {
                addressState.value = it
            }
        }
    }

    override fun getAddressState(): State<String> = addressState
}