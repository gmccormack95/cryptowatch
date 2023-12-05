@file:OptIn(ExperimentalAnimationGraphicsApi::class)

package com.link.stinkies.layout.settings

import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import android.text.util.Linkify
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.activity.compose.BackHandler
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.android.material.textview.MaterialTextView
import com.link.stinkies.R
import com.link.stinkies.ui.theme.darkLinkBlue
import com.link.stinkies.ui.theme.linkBlue
import com.link.stinkies.viewmodel.activity.HomeActivityVM
import java.lang.Math.min

@Composable
fun SettingsLayout(viewModel: HomeActivityVM) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .drawWithContent {
                    drawContent()
                    drawPath(
                        path = drawCustomHexagonPath(size),
                        color = Color.White,
                        style = Stroke(
                            width = 16.dp.toPx(),
                            pathEffect = PathEffect.cornerPathEffect(0f)
                        )
                    )
                }
                .wrapContentSize()
                .align(Alignment.TopCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.spilt_stinkies),
                contentDescription = "No No Linkers",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .wrapContentSize()
                    .graphicsLayer {
                        shadowElevation = 8.dp.toPx()
                        shape = HexagonShape()
                        clip = true
                    }
                    .background(color = Color.Cyan)
                    .height(250.dp)
            )
        }
    }
}

@Composable
fun WebviewScreen(){
    var backEnable by remember { mutableStateOf(false) }
    var webView : WebView? = null

    AndroidView(
        modifier = Modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                webViewClient = object : WebViewClient(){
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        backEnable = view!!.canGoBack()
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl("https://staking.chain.link/")
                webView = this
            }
        }, update = {
            webView = it
        })
    BackHandler(enabled = backEnable) {
        webView?.goBack()
    }
}

class HexagonShape : Shape {

    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawCustomHexagonPath(size)
        )
    }
}

fun drawCustomHexagonPath(size: androidx.compose.ui.geometry.Size): Path {
    return Path().apply {
        val radius = min(size.width / 2f, size.height / 2f)
        customHexagon(radius, size)
    }
}

fun Path.customHexagon(radius: Float, size: androidx.compose.ui.geometry.Size) {
    val triangleHeight = (kotlin.math.sqrt(3.0) * radius / 2)
    val centerX = size.width / 2
    val centerY = size.height / 2

    moveTo(centerX.toFloat(), centerY + radius)
    lineTo((centerX - triangleHeight).toFloat(), centerY + radius/2)
    lineTo((centerX - triangleHeight).toFloat(), centerY - radius/2)
    lineTo(centerX.toFloat(), centerY - radius)
    lineTo((centerX + triangleHeight).toFloat(), centerY - radius/2)
    lineTo((centerX + triangleHeight).toFloat(), centerY + radius/2)

    close()
}