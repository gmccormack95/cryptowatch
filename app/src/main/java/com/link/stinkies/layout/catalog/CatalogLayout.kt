@file:OptIn(ExperimentalGlideComposeApi::class)

package com.link.stinkies.layout.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.link.stinkies.model.biz.ThreadItem
import com.link.stinkies.viewmodel.activity.HomeActivityVM
import androidx.compose.ui.graphics.Color

@Composable
fun CatalogLayout(viewModel: HomeActivityVM) {
    val catalog = viewModel.catalog.observeAsState()

    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp
            )
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(catalog.value?.threads?.count() ?: 0) { index ->
                    Thread(
                        thread = catalog.value?.threads?.get(index)
                    )
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun Thread(thread: ThreadItem?) {
    if(thread?.stickied == true || thread?.locked == true) return

    Column(
        modifier = Modifier
            .clip(
                RoundedCornerShape(8.dp, 8.dp, 8.dp, 8.dp)
            )
            .background(Color.White)
    ) {
        GlideImage(
            model = thread?.imageUrl,
            contentDescription = "station image",
            transition = CrossFade,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(150.dp)
                .clip(
                    RoundedCornerShape(8.dp, 8.dp, 0.dp, 0.dp)
                )
        )
        thread?.sub?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                maxLines = 2,
                modifier = Modifier
                    .padding(top = 8.dp, start = 4.dp, end = 4.dp)
            )
        }
        Text(
            text = thread?.comment ?: "",
            color = Color.Black,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            maxLines = 2,
            modifier = Modifier
                .padding(top = 4.dp, start = 4.dp, end = 4.dp, bottom = 8.dp)
        )
    }
}