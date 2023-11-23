@file:OptIn(ExperimentalGlideComposeApi::class)

package com.link.stinkies.layout.activity.home.thread.post

import android.text.util.Linkify
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.android.material.textview.MaterialTextView
import com.link.stinkies.model.biz.Post
import com.link.stinkies.ui.theme.white
import com.link.stinkies.view.PostReplySpan
import com.link.stinkies.view.PostReplyMovementMethod
import com.link.stinkies.viewmodel.activity.HomeActivityVM
import kotlinx.coroutines.launch


@Composable
fun Post(viewModel: HomeActivityVM, post: Post?, modifier: Modifier) {
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
        PostHeader(
            viewModel = viewModel,
            post = post
        )
        PostBody(
            viewModel = viewModel,
            post = post
        )
        PostFooter(
            viewModel = viewModel,
            post = post
        )
    }
}

@Composable
fun PostHeader(viewModel: HomeActivityVM, post: Post?) {
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
                onClick = { viewModel.showSheet.value = true },
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
fun PostBody(viewModel: HomeActivityVM, post: Post?) {
    val drawerState = viewModel.drawerState
    val scope = rememberCoroutineScope()

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
                    movementMethod = PostReplyMovementMethod { postId ->
                        scope.launch {
                            viewModel.threadLayoutVM.repliesDrawerVM.listState.scrollToItem(0)
                            viewModel.threadLayoutVM.getReplies(postId)
                            drawerState.open()
                        }
                        true
                    }
                }
            },
            update = { textView ->
                textView.text = post?.spannedComment
                PostReplySpan.span(textView)
            },
        )
    }
}

@Composable
private fun PostFooter(viewModel: HomeActivityVM, post: Post?) {
    Box(
        modifier = Modifier
            .background(Color.LightGray)
    )
}

@Composable
private fun PostImage(post: Post?) {
    val expandImage = post?.expanded?.observeAsState()

    post?.imageUrl?.run {
        if(expandImage?.value == true) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(this)
                    .crossfade(true)
                    .build(),
                contentDescription = "Post Image",
                modifier = Modifier
                    .clickable {
                        post.expanded.value = !(expandImage?.value ?: false)
                    }
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(this)
                    .crossfade(true)
                    .build(),
                contentDescription = "Post Image",
                modifier = Modifier
                    .heightIn(max = 150.dp)
                    .clickable {
                        post.expanded.value = !(expandImage?.value ?: false)
                    }
            )
        }
    }
}