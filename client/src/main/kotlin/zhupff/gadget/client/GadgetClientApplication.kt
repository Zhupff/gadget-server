package zhupff.gadget.client

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import zhupff.gadget.client.basic.Logo
import zhupff.gadget.client.basic.WINDOW_MIN_HEIGHT
import zhupff.gadget.client.basic.WINDOW_MIN_WIDTH
import zhupff.gadget.client.basic.WINDOW_TITLE_BAR_HEIGHT
import zhupff.gadget.server.GadgetServerApplication
import java.awt.Dimension

internal fun main() {
    GadgetClientApplication.run()
}


object GadgetClientApplication : ClientApi, Runnable {

    init {
        ClientApi.compareAndSet(null, this)
    }

    override fun run() {

        val runServer = false

        application {

            val isUndecorated by remember { mutableStateOf(true) }

            Window(
                onCloseRequest = ::exitApplication,
                state = rememberWindowState(width = WINDOW_MIN_WIDTH.dp, height = WINDOW_MIN_HEIGHT.dp),
                title = "Gadget Server",
                undecorated = isUndecorated,
                transparent = true,
            ) {
                window.minimumSize = Dimension(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT)

                if (isUndecorated) {
                    WindowDraggableArea(
                        modifier = Modifier
                            .fillMaxWidth().height(WINDOW_TITLE_BAR_HEIGHT.dp)
                    ) {
                    }
                }

                App(
                    runServer = runServer,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            if (runServer) {
                GadgetServerApplication.run()
            }
        }
    }

    @Composable
    fun App(
        runServer: Boolean,
        modifier: Modifier,
    ) {

        Box(
            modifier = modifier
        ) {
            Wallpaper(
                mask = Color.Transparent,
                modifier = Modifier.fillMaxSize()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxHeight()
                        .background(Color.Black.copy(alpha = 0.25f))
                        .padding(10.dp, 0.dp, 10.dp, 0.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth().height(WINDOW_TITLE_BAR_HEIGHT.dp)
                            .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    ) {
                        Logo(
                            modifier = Modifier
                                .width(48.dp).height(48.dp)
                        )

                        Text(
                            text = "Gadget",
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize().weight(1F)
                        .background(Color.White.copy(alpha = 0.25f))
                ) {
                }
            }
        }
    }

    @Composable
    fun TitleBar(
        modifier: Modifier = Modifier,
    ) {

    }

    override fun getTestStr(): String = "Welcome to Gadget Client"
}