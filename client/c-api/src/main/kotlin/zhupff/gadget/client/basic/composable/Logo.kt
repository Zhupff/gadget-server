package zhupff.gadget.client.basic.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

private fun getLogoImageVector(
    backgroundColor: Color,
    partAColor: Color,
    partBColor: Color,
): ImageVector {
    return ImageVector.Builder(
        defaultWidth = 108.dp,
        defaultHeight = 108.dp,
        viewportWidth = 24F,
        viewportHeight = 24F,
    ).addPath(
        pathData = listOf(
            PathNode.MoveTo(0F, 0F),
            PathNode.LineTo(24F, 0F),
            PathNode.LineTo(24F, 24F),
            PathNode.LineTo(0F, 24F),
            PathNode.Close,
        ),
        fill = Brush.linearGradient(
            listOf(backgroundColor, backgroundColor),
            end = Offset(24F, 24F)
        ),
    ).addGroup(
        scaleX = 0.8F,
        scaleY = 0.8F,
        translationX = 2.4F,
        translationY = 2.4F,
    ).addPath(
        pathData = listOf(
            PathNode.MoveTo(17.5F,10F),
            PathNode.RelativeCurveTo(1.93F,0F,3.5F,-1.57F,3.5F,-3.5F),
            PathNode.RelativeCurveTo(0F,-0.58F,-0.16F,-1.12F,-0.41F,-1.6F),
            PathNode.RelativeLineTo(-2.7F,2.7F),
            PathNode.LineTo(16.4F,6.11F),
            PathNode.RelativeLineTo(2.7F,-2.7F),
            PathNode.CurveTo(18.62F,3.16F,18.08F,3F,17.5F,3F),
            PathNode.CurveTo(15.57F,3F,14F,4.57F,14F,6.5F),
            PathNode.RelativeCurveTo(0F,0.41F,0.08F,0.8F,0.21F,1.16F),
            PathNode.RelativeLineTo(-1.85F,1.85F),
            PathNode.RelativeLineTo(-1.78F,-1.78F),
            PathNode.RelativeLineTo(0.71F,-0.71F),
            PathNode.LineTo(9.88F,5.61F),
            PathNode.LineTo(12F,3.49F),
            PathNode.RelativeCurveTo(-1.17F,-1.17F,-3.07F,-1.17F,-4.24F,0F),
            PathNode.LineTo(4.22F,7.03F),
            PathNode.RelativeLineTo(1.41F,1.41F),
            PathNode.HorizontalTo(2.81F),
            PathNode.LineTo(2.1F,9.15F),
            PathNode.RelativeLineTo(3.54F,3.54F),
            PathNode.RelativeLineTo(0.71F,-0.71F),
            PathNode.VerticalTo(9.15F),
            PathNode.RelativeLineTo(1.41F,1.41F),
            PathNode.RelativeLineTo(0.71F,-0.71F),
            PathNode.RelativeLineTo(1.78F,1.78F),
            PathNode.RelativeLineTo(-7.41F,7.41F),
            PathNode.RelativeLineTo(2.12F,2.12F),
            PathNode.LineTo(16.34F,9.79F),
            PathNode.CurveTo(16.7F,9.92F,17.09F,10F,17.5F,10F),
            PathNode.Close,
        ),
        fill = Brush.linearGradient(listOf(partAColor, partAColor))
    ).addGroup(
        scaleX = 0.42F,
        scaleY = 0.42F,
        translationX = 12F,
        translationY = 12F,
    ).addPath(
        pathData = listOf(
            PathNode.MoveTo(19.14F,12.94F),
            PathNode.RelativeCurveTo(0.04F,-0.3F,0.06F,-0.61F,0.06F,-0.94F),
            PathNode.RelativeCurveTo(0F,-0.32F,-0.02F,-0.64F,-0.07F,-0.94F),
            PathNode.RelativeLineTo(2.03F,-1.58F),
            PathNode.RelativeCurveTo(0.18F,-0.14F,0.23F,-0.41F,0.12F,-0.61F),
            PathNode.RelativeLineTo(-1.92F,-3.32F),
            PathNode.RelativeCurveTo(-0.12F,-0.22F,-0.37F,-0.29F,-0.59F,-0.22F),
            PathNode.RelativeLineTo(-2.39F,0.96F),
            PathNode.RelativeCurveTo(-0.5F,-0.38F,-1.03F,-0.7F,-1.62F,-0.94F),
            PathNode.LineTo(14.4F,2.81F),
            PathNode.RelativeCurveTo(-0.04F,-0.24F,-0.24F,-0.41F,-0.48F,-0.41F),
            PathNode.RelativeHorizontalTo(-3.84F),
            PathNode.RelativeCurveTo(-0.24F,0F,-0.43F,0.17F,-0.47F,0.41F),
            PathNode.LineTo(9.25F,5.35F),
            PathNode.CurveTo(8.66F,5.59F,8.12F,5.92F,7.63F,6.29F),
            PathNode.LineTo(5.24F,5.33F),
            PathNode.RelativeCurveTo(-0.22F,-0.08F,-0.47F,0F,-0.59F,0.22F),
            PathNode.LineTo(2.74F,8.87F),
            PathNode.CurveTo(2.62F,9.08F,2.66F,9.34F,2.86F,9.48F),
            PathNode.RelativeLineTo(2.03F,1.58F),
            PathNode.CurveTo(4.84F,11.36F,4.8F,11.69F,4.8F,12F),
            PathNode.RelativeQuadTo(0.02F,0.64F,0.07F,0.94F),
            PathNode.RelativeLineTo(-2.03F,1.58F),
            PathNode.RelativeCurveTo(-0.18F,0.14F,-0.23F,0.41F,-0.12F,0.61F),
            PathNode.RelativeLineTo(1.92F,3.32F),
            PathNode.RelativeCurveTo(0.12F,0.22F,0.37F,0.29F,0.59F,0.22F),
            PathNode.RelativeLineTo(2.39F,-0.96F),
            PathNode.RelativeCurveTo(0.5F,0.38F,1.03F,0.7F,1.62F,0.94F),
            PathNode.RelativeLineTo(0.36F,2.54F),
            PathNode.RelativeCurveTo(0.05F,0.24F,0.24F,0.41F,0.48F,0.41F),
            PathNode.RelativeHorizontalTo(3.84F),
            PathNode.RelativeCurveTo(0.24F,0F,0.44F,-0.17F,0.47F,-0.41F),
            PathNode.RelativeLineTo(0.36F,-2.54F),
            PathNode.RelativeCurveTo(0.59F,-0.24F,1.13F,-0.56F,1.62F,-0.94F),
            PathNode.RelativeLineTo(2.39F,0.96F),
            PathNode.RelativeCurveTo(0.22F,0.08F,0.47F,0F,0.59F,-0.22F),
            PathNode.RelativeLineTo(1.92F,-3.32F),
            PathNode.RelativeCurveTo(0.12F,-0.22F,0.07F,-0.47F,-0.12F,-0.61F),
            PathNode.LineTo(19.14F,12.94F),
            PathNode.Close,
            PathNode.MoveTo(12F,15.6F),
            PathNode.RelativeCurveTo(-1.98F,0F,-3.6F,-1.62F,-3.6F,-3.6F),
            PathNode.RelativeQuadTo(1.62F,-3.6F,3.6F,-3.6F),
            PathNode.RelativeQuadTo(3.6F,1.62F,3.6F,3.6F),
            PathNode.QuadTo(13.98F,15.6F,12F,15.6F),
            PathNode.Close,
        ),
        fill = Brush.linearGradient(listOf(partBColor, partBColor))
    ).build()
}

@Composable
fun Logo(
    modifier: Modifier
) {
    Image(
        painter = rememberVectorPainter(getLogoImageVector(Color.Black, Color.White, Color(0xFF1997DD))),
        contentDescription = null,
        modifier = modifier
    )
}


@Composable
@Preview
private fun LogoPreview() {
    Logo(
        modifier = Modifier
            .width(200.dp).height(200.dp)
    )
}