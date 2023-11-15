package com.link.stinkies.layout.activity.intro

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.link.stinkies.R
import com.link.stinkies.layout.settings.HexagonShape
import com.link.stinkies.layout.settings.drawCustomHexagonPath
import com.link.stinkies.ui.theme.StinkiesTheme
import com.link.stinkies.ui.theme.linkBlue

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun IntroActivityLayout(splashAnimation: Drawable?) {
    StinkiesTheme(
        dynamicColor = false
    ) {
        Scaffold() { paddingValues ->
            Surface {
                Box(modifier = Modifier){
                    AndroidView(
                        modifier = Modifier,
                        factory = {
                            ImageView(it)
                        },
                        update = {
                            it.background = splashAnimation
                            val splashAnim = it.background as? AnimationDrawable
                            if (splashAnim?.isRunning == false) {
                                splashAnim.start()
                            }
                        }
                    )
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .drawWithContent {
                                drawContent()
                                drawPath(
                                    path = drawCustomHexagonPath(size),
                                    color = linkBlue,
                                    style = Stroke(
                                        width = 8.dp.toPx(),
                                        pathEffect = PathEffect.cornerPathEffect(16f)
                                    )
                                )
                            }
                            .wrapContentSize()
                            .align(Alignment.Center)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.no_no_linkers_splash),
                            contentDescription = "No No Linkers",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .wrapContentSize()
                                .graphicsLayer {
                                    shadowElevation = 8.dp.toPx()
                                    shape = HexagonShape()
                                    clip = true
                                }
                                .height(250.dp)

                        )
                    }
                }
            }
        }
    }
}