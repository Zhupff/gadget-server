import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlinx.coroutines.*
import zhupff.gadget.client.app.GadgetClientApplication
import zhupff.gadget.database.DataBaseApi
import zhupff.gadget.server.app.GadgetServerApplication
import java.awt.Dimension

fun main() {
    runBlocking {
        DataBaseApi.prepare()
        GadgetClientApplication.prepare()
        GadgetServerApplication.prepare()
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            state = rememberWindowState(width = 1080.dp, height = 720.dp),
            title = "Gadget Server",
        ) {
            window.minimumSize = Dimension(1080, 720)
            GadgetClientApplication.App()
        }
        GadgetServerApplication.run()
    }
}
