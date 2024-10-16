package zhupff.gadget.client.app

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toPainter
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.*
import zhupff.gadget.client.ClientApi
import zhupff.gadget.common.model.ServerUrlAction
import java.awt.image.BufferedImage

object GadgetClientApplication {

    fun prepare() {
        GlobalScope.launch {
            if (ClientApi.compareAndSet(false, true)) {
                ClientApi
                println("ClientApi prepared in ${Thread.currentThread()}")
            }
        }
    }

@Composable
@Preview
fun App() {

    val action = ServerUrlAction(ClientApi.baseUrl)
    val bitMatrix = QRCodeWriter().encode(action.toString(), BarcodeFormat.QR_CODE, 512, 512)
    val bufferedImage = BufferedImage(bitMatrix.width, bitMatrix.height, BufferedImage.TYPE_INT_ARGB)
    for (h in 0 until bitMatrix.height) {
        for (w in 0 until bitMatrix.width) {
            bufferedImage.setRGB(w, h, if (bitMatrix.get(w, h)) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
        }
    }
    @Composable
    @Preview
    fun App() {
        val url: String by remember { ClientApi.getAddressState() }
        val action = ServerUrlAction(url)
        val bitMatrix = QRCodeWriter().encode(action.toString(), BarcodeFormat.QR_CODE, 512, 512)
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
}