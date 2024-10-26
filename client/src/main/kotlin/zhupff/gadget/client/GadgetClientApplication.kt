package zhupff.gadget.client

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import zhupff.gadget.server.GadgetServerApplication
import java.awt.Dimension
import java.awt.image.BufferedImage

internal fun main() {
    GadgetClientApplication.run()
}



object GadgetClientApplication : ClientApi, Runnable {

    init {
        ClientApi.compareAndSet(null, this)
    }

    override fun run() {
        application {
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
    }

    @Composable
    @Preview
    fun App() {
        val bitMatrix = QRCodeWriter().encode(ClientApi.get().getTestStr(), BarcodeFormat.QR_CODE, 512, 512)
        val bufferedImage = BufferedImage(bitMatrix.width, bitMatrix.height, BufferedImage.TYPE_INT_ARGB)
        for (h in 0 until bitMatrix.height) {
            for (w in 0 until bitMatrix.width) {
                bufferedImage.setRGB(w, h, if (bitMatrix.get(w, h)) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
            }
        }

        MaterialTheme {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Image(
                    painter = bufferedImage.toPainter(),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

    override fun getTestStr(): String = "Welcome to Gadget Client"
}