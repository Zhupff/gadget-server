package zhupff.gadget.server.app

import com.google.auto.service.AutoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import zhupff.gadget.server.ServerApi

@AutoService(ServerApi::class)
class ServerApiInstance : ServerApi,
    CoroutineScope by GlobalScope
{

    private val address: MutableSharedFlow<String> = MutableSharedFlow(1)

    override suspend fun getAddress(): Flow<String> {
        return address
    }

    override fun onAddressChanged(ip: String, port: Int) {
        val newAddress = "$ip:$port"
        launch {
            address.emit(newAddress)
        }
    }
}