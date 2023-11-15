@file:OptIn(ExperimentalGlideComposeApi::class)

package com.link.stinkies.layout.activity.home.thread.post

import android.text.util.Linkify
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.android.material.textview.MaterialTextView
import com.link.stinkies.model.biz.Post
import com.link.stinkies.ui.theme.white


@Composable
fun Post(post: Post?, modifier: Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(0),
        colors = CardDefaults.cardColors(containerColor = white),
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
    ) {
        PostHeader(post = post)
        PostBody(post = post)
    }
}

@Composable
fun PostHeader(post: Post?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.6F))
            .padding(8.dp)
    ) {
        post?.sub?.let {
            Text(
                text = it,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${post?.name} (ID: ${post?.userId ?: ""})",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier

            )
            Spacer(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Text(
                text = "${post?.id}",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(end = 16.dp)
            )
            IconButton(
                onClick = {

                },
                modifier = Modifier
                    .size(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun PostBody(post: Post?) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        PostImage(
            post = post
        )

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
                it.text = post?.spannedComment
            }
        )
    }
}

@Composable
private fun PostImage(post: Post?) {
    var expandImage = post?.expanded?.observeAsState()

    post?.imageUrl?.let {
        if(expandImage?.value == true) {
            GlideImage(
                model = it,
                contentDescription = "station image",
                transition = CrossFade,
                modifier = Modifier
                    .clickable {
                        post.expanded.value = !(expandImage.value ?: false)
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
                        post.expanded.value = !(expandImage?.value ?: false)
                    }
            )
        }

    }
}