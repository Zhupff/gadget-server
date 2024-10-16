package zhupff.gadget.server.app

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zhupff.gadget.server.ServerApi

@RestController
class HomeController {

    @RequestMapping("/home")
    fun welcome(): String {
        return ServerApi.Home.welcome()
    }
}