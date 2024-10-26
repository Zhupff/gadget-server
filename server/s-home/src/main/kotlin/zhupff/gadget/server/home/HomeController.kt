package zhupff.gadget.server.home

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zhupff.gadget.server.ServerApi

@RestController
class HomeController {

    @RequestMapping("/home")
    fun home(): String {
        return ServerApi.get().getTestStr()
    }
}