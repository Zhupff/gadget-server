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
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.awt.image.BufferedImage


@Composable
@Preview
fun App() {
    var baseUrl by remember { mutableStateOf("hello world") }

    val bm = QRCodeWriter().encode(baseUrl, BarcodeFormat.QR_CODE, 512, 512)
    val bi = BufferedImage(bm.width, bm.height, BufferedImage.TYPE_INT_ARGB)
    for (h in 0 until bm.height) {
        for (w in 0 until bm.width) {
            val bool = bm.get(w, h)
            bi.setRGB(w, h, if (bool) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
        }
    }

    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = bi.toPainter(),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}