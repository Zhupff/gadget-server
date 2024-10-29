package zhupff.gadget.server.res

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.io.File

@Configuration
class LocalResConfigurer : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        try {
            val localResourceDir = File("").resolve("localres")
            val checkFile = localResourceDir.resolve("localres.txt")
            val exists = checkFile.readText().isNotEmpty()
            if (exists) {
                registry.addResourceHandler("/res/**")
                    .addResourceLocations("file:${localResourceDir.absolutePath}/")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}