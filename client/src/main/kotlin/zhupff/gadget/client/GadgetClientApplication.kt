package zhupff.gadget.client

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.*
import zhupff.gadget.client.basic.WINDOW_MIN_HEIGHT
import zhupff.gadget.client.basic.WINDOW_MIN_WIDTH
import zhupff.gadget.client.basic.WINDOW_TITLE_BAR_HEIGHT
import zhupff.gadget.client.basic.composable.CloseWindowImage
import zhupff.gadget.client.basic.composable.Logo
import zhupff.gadget.client.basic.composable.MaximizeWindowImage
import zhupff.gadget.client.basic.composable.MinimizeWindowImage
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

        application {

            val isUndecorated by remember { mutableStateOf(true) }
            val windowState = rememberWindowState(
                width = WINDOW_MIN_WIDTH.dp,
                height = WINDOW_MIN_HEIGHT.dp,
                position = WindowPosition(Alignment.Center)
            )
            val windowPanelControl by remember(windowState) {
                mutableStateOf(
                    WindowPanelControl(
                        onCloseWindow = {
                            GadgetServerApplication.exit()
                            exitApplication()
                        },
                        onMinimizeWindow = {
                            windowState.isMinimized = true
                        },
                        onMaximizeWindow = {
                            if (windowState.placement == WindowPlacement.Maximized) {
                                windowState.placement = WindowPlacement.Floating
                            } else {
                                windowState.placement = WindowPlacement.Maximized
                            }
                        },
                        windowState = windowState,
                    )
                )
            }

            Window(
                onCloseRequest = windowPanelControl.onCloseWindow,
                state = windowState,
                title = "Gadget Server",
                undecorated = isUndecorated,
                transparent = isUndecorated,
            ) {
                window.minimumSize = Dimension(WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT)

                if (isUndecorated) {
                    WindowDraggableArea(
                        modifier = Modifier
                            .fillMaxWidth().height(WINDOW_TITLE_BAR_HEIGHT.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onDoubleTap = { windowPanelControl.onMaximizeWindow() }
                                )
                            }
                    ) {
                    }
                }

                App(
                    windowPanelControl = windowPanelControl,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }


    @Composable
    private fun App(
        windowPanelControl: WindowPanelControl,
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
                var selectedOptionName by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxHeight()
                        .background(Color.Black.copy(alpha = 0.25f))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth().height(WINDOW_TITLE_BAR_HEIGHT.dp)
                            .padding(16.dp, 10.dp, 16.dp, 0.dp)
                    ) {
                        Logo(
                            modifier = Modifier
                                .width(48.dp).height(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onDoubleTap = {
                                            if (GadgetServerApplication.isRunning) {
                                                GadgetServerApplication.exit()
                                            } else {
                                                GadgetServerApplication.run()
                                            }
                                        }
                                    )
                                }
                        )

                        Text(
                            text = "Gadget",
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(0.dp, 4.dp, 0.dp, 0.dp)
                        )
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize().weight(1F)
                            .padding(0.dp, 16.dp, 0.dp, 0.dp)
                    ) {
                        items(
                            items = listOf(
                                "images/ic_home.svg" to "首页",
                                "images/ic_music.svg" to "音乐",
                                "images/ic_image.svg" to "图片",
                                "images/ic_video.svg" to "视频",
                                "images/ic_game.svg" to "游戏",
                                "images/ic_subscribe.svg" to "订阅",
                                "images/ic_favorite.svg" to "收藏",
                                "images/ic_chat.svg" to "聊天",
                            )
                        ) { option ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth().height(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            selectedOptionName = option.second
                                        }
                                ) {
                                    Icon(
                                        painter = painterResource(option.first),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(16.dp, 0.dp, 0.dp, 0.dp)
                                            .size(24.dp)
                                    )
                                    Text(
                                        text = option.second,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Thin,
                                        textAlign = TextAlign.Start,
                                        modifier = Modifier
                                            .fillMaxWidth().wrapContentHeight().weight(1F)
                                            .padding(0.dp, 0.dp, 16.dp, 0.dp)
                                    )
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(16.dp, 17.dp, 0.dp, 17.dp)
                            .size(24.dp)
                            .clip(RoundedCornerShape(4.dp))
                    ) {
                        Icon(
                            painter = painterResource("images/ic_setting.svg"),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .fillMaxSize()
                                .clickable {  }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize().weight(1F)
                        .background(Color.White.copy(alpha = 0.25f))
                        .padding(16.dp, 0.dp, 16.dp, 0.dp)
                ) {
                    TitleBar(
                        windowPanelControl = windowPanelControl,
                        selectedOptionName,
                        modifier = Modifier
                            .fillMaxWidth().height(WINDOW_TITLE_BAR_HEIGHT.dp)
                            .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    )
                }
            }
        }
    }


    @Composable
    private fun TitleBar(
        windowPanelControl: WindowPanelControl,
        selectedOptionName: String,
        modifier: Modifier = Modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize().weight(1F)
            ) {

                Text(
                    text = selectedOptionName,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .padding(0.dp, 4.dp, 0.dp, 0.dp)
                        .wrapContentSize()
                )

            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(0.dp, 4.dp, 0.dp, 4.dp)
                    .wrapContentWidth().fillMaxHeight()
                    .clip(CircleShape)
                    .background(LocalContentColor.current.copy(0.3F))
            ) {
                Icon(
                    painter = painterResource("images/ic_skull.svg"),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp, 0.dp, 0.dp, 0.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(0.5F))
                )
                Text(
                    text = "SignIn / SignUp",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Normal,
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 8.dp, 0.dp)
                        .wrapContentSize()
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentWidth().fillMaxHeight()
            ) {
                MinimizeWindowImage(
                    onClick = windowPanelControl.onMinimizeWindow,
                    modifier = Modifier
                        .width(32.dp).height(32.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                MaximizeWindowImage(
                    onClick = windowPanelControl.onMaximizeWindow,
                    maximized = windowPanelControl.windowState.placement == WindowPlacement.Maximized,
                    modifier = Modifier
                        .width(32.dp).height(32.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
                CloseWindowImage(
                    onClick = windowPanelControl.onCloseWindow,
                    modifier = Modifier
                        .width(32.dp).height(32.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
        }
    }




    private class WindowPanelControl(
        val onMinimizeWindow: () -> Unit,
        val onMaximizeWindow: () -> Unit,
        val onCloseWindow: () -> Unit,
        val windowState: WindowState,
    )

    override fun getTestStr(): String = "Welcome to Gadget Client"
}