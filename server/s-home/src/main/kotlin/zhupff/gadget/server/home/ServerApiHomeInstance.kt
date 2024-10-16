package zhupff.gadget.server.home

import com.google.auto.service.AutoService
import zhupff.gadget.server.ServerApi

@AutoService(ServerApi.Home::class)
class ServerApiHomeInstance : ServerApi.Home {

    override fun welcome(): String {
        ServerApi.onAddressChanged("wtfu", 2313)
        return "Welcome to Gadget!"
    }
}