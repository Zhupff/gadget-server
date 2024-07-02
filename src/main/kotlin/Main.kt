import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import zhupff.gadget.App
import zhupff.gadget.GadgetServerApplication

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
    GadgetServerApplication.run()
}
