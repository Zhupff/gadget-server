package zhupff.gadget.client.basic.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

private fun getCloseImageVector(
    color: Color,
): ImageVector {
    return ImageVector.Builder(
        defaultWidth = 200.dp,
        defaultHeight = 200.dp,
        viewportWidth = 1024F,
        viewportHeight = 1024F,
    ).addPath(
        pathData = listOf(
            PathNode.MoveTo(176.661601F,817.172881F),
            PathNode.CurveTo(168.472798F,825.644055F,168.701706F,839.149636F,177.172881F,847.338438F),
            PathNode.CurveTo(185.644056F,855.527241F,199.149636F,855.298332F,207.338438F,846.827157F),
            PathNode.LineTo(826.005105F,206.827157F),
            PathNode.CurveTo(834.193907F,198.355983F,833.964998F,184.850403F,825.493824F,176.661601F),
            PathNode.CurveTo(817.02265F,168.472798F,803.517069F,168.701706F,795.328267F,177.172881F),
            PathNode.LineTo(176.661601F,817.172881F),
            PathNode.Close,
            PathNode.MoveTo(795.328267F,846.827157F),
            PathNode.CurveTo(803.517069F,855.298332F,817.02265F,855.527241F,825.493824F,847.338438F),
            PathNode.CurveTo(833.964998F,839.149636F,834.193907F,825.644055F,826.005105F,817.172881F),
            PathNode.LineTo(207.338438F,177.172881F),
            PathNode.CurveTo(199.149636F,168.701706F,185.644056F,168.472798F,177.172881F,176.661601F),
            PathNode.CurveTo(168.701706F,184.850403F,168.472798F,198.355983F,176.661601F,206.827157F),
            PathNode.LineTo(795.328267F,846.827157F),
            PathNode.Close,
        ),
        fill = Brush.linearGradient(listOf(color, color))
    ).build()
}

@Composable
fun CloseImage(
    onClick: () -> Unit,
    modifier: Modifier,
) {
    Box(
        modifier = modifier.clickable(enabled = false) {  }
    ) {
        Image(
            painter = rememberVectorPainter(getCloseImageVector(Color.Black)),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick)
        )
    }
}