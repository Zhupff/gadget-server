package zhupff.gadget.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @RequestMapping("/home")
    fun welcome(): String {
        return "Welcome to Gadget!"
    }
}