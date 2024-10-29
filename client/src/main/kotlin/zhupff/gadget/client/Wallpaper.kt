package zhupff.gadget.client

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import java.io.File

@Composable
fun Wallpaper(
    alpha: Float = 1f,
    mask: Color = Color.White.copy(0.33F),
    modifier: Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        val image = remember {
            val file = File("").resolve("localres/wallpaper.jpg")
            loadImageBitmap(file.inputStream())
        }

        Image(
            bitmap = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = alpha,
            modifier = modifier,
        )

        Image(
            painter = ColorPainter(mask),
            contentDescription = null,
            modifier = modifier,
        )
    }
}