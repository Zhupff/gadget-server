import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import zhupff.gadget.client.App
import zhupff.gadget.server.GadgetServerApplication
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(width = 1080.dp, height = 720.dp),
        title = "Gadget Server",
    ) {
        window.minimumSize = Dimension(1080, 720)
        App()
    }
    GadgetServerApplication.run()
}
