package zhupff.gadget.server

import kotlinx.coroutines.flow.Flow
import java.util.ServiceLoader
import java.util.concurrent.atomic.AtomicBoolean

interface ServerApi {

    companion object : ServerApi by ServiceLoader.load(ServerApi::class.java).first(),
        AtomicBoolean(false)
    {
    }

    suspend fun getAddress(): Flow<String>

    fun onAddressChanged(ip: String, port: Int)



    interface Home {
        companion object : Home by ServiceLoader.load(Home::class.java).first()

        fun home(): String
    }
}