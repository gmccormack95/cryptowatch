@file:OptIn(ExperimentalGlideComposeApi::class)

package com.link.stinkies.layout.activity.home.thread.post

import android.text.util.Linkify
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.android.material.textview.MaterialTextView
import com.link.stinkies.model.biz.Post

@Composable
fun Post(post: Post?) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
    ) {

        PostHeader(post = post)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.75.dp)
                .background(color = Color.LightGray)
        )
    }
}

@Composable
fun PostHeader(post: Post?) {
    var expandImage by remember {
        mutableStateOf(post?.expanded)
    }

    Row(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
        ) {
            Text(
                text = "${post?.name} (ID: ${post?.userId ?: ""})",
                fontSize = 12.sp,
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            post?.imageUrl?.let {
                if(expandImage == true) {
                    GlideImage(
                        model = it,
                        contentDescription = "station image",
                        transition = CrossFade,
                        modifier = Modifier
                            .clickable {
                                expandImage = !(expandImage ?: false)
                                post.expanded = !(expandImage ?: false)
                            }
                    )
                } else {
                    GlideImage(
                        model = it,
                        contentDescription = "station image",
                        contentScale = ContentScale.Fit,
                        transition = CrossFade,
                        modifier = Modifier
                            .heightIn(max = 150.dp)
                            .clickable {
                                expandImage = !(expandImage ?: false)
                                post.expanded = !(expandImage ?: false)
                            }
                    )
                }

            }

            post?.sub?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                        )
                )
            }
            AndroidView(
                modifier = Modifier
                    .padding(top = 4.dp, start = 4.dp, end = 4.dp, bottom = 8.dp),
                factory = {
                    MaterialTextView(it).apply {
                        autoLinkMask = Linkify.WEB_URLS
                        linksClickable = true
                        setTextColor(Color.DarkGray.toArgb())
                        setLinkTextColor(Color.Blue.toArgb())
                    }
                },
                update = {
                    it.text = post?.comment
                }
            )
        }
    }
}