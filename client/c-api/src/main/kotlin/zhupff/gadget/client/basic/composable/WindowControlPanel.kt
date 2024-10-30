package zhupff.gadget.client.basic.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun MinimizeWindowImage(
    onClick: () -> Unit,
    modifier: Modifier,
) {
    Box(
        modifier = modifier.clickable(enabled = false) {  }
    ) {
        Icon(
            painter = painterResource("images/minimize_window.svg"),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick)
        )
    }
}


@Composable
fun MaximizeWindowImage(
    onClick: () -> Unit,
    maximized: Boolean,
    modifier: Modifier,
) {
    Box(
        modifier = modifier.clickable(enabled = false) {  }
    ) {
        val painter = if (maximized) {
            painterResource("images/maximized_window.svg")
        } else {
            painterResource("images/maximize_window.svg")
        }
        Icon(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick)
        )
    }
}


@Composable
fun CloseWindowImage(
    onClick: () -> Unit,
    modifier: Modifier,
) {
    Box(
        modifier = modifier.clickable(enabled = false) {  }
    ) {
        Icon(
            painter = painterResource("images/close_window.svg"),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = onClick)
        )
    }
}



//@Composable
//@Preview
//private fun WindowControlPanelPreview() {
//    Column(
//        modifier = Modifier
//            .width(200.dp).height(600.dp)
//            .background(Color.Gray.copy(alpha = 0.3f)),
//    ) {
//        MinimizeWindowImage(
//            onClick = {},
//            modifier = Modifier
//                .fillMaxSize().weight(1F)
//        )
//        MaximizeWindowImage(
//            onClick = {},
//            modifier = Modifier
//                .fillMaxSize().weight(1F)
//        )
//        CloseWindowImage(
//            onClick = {},
//            modifier = Modifier
//                .fillMaxSize().weight(1F)
//        )
//    }
//}