@file:OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class,
    ExperimentalGlideComposeApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterialApi::class, ExperimentalMaterialApi::class, ExperimentalMaterialApi::class
)

package com.link.stinkies.layout.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.link.stinkies.model.biz.ThreadItem
import com.link.stinkies.viewmodel.activity.HomeActivityVM
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.bumptech.glide.integration.compose.GlideSubcomposition
import com.bumptech.glide.integration.compose.RequestBuilderTransform
import com.bumptech.glide.integration.compose.RequestState
import com.link.stinkies.layout.activity.home.Screen
import com.link.stinkies.ui.theme.background
import com.link.stinkies.ui.theme.white

@Composable
fun CatalogLayout(viewModel: HomeActivityVM, navController: NavController, gridState: LazyStaggeredGridState) {
    val catalog = viewModel.catalog.observeAsState()
    val isRefreshing = viewModel.catalogLoading.observeAsState()
    val pullRefreshState = rememberPullRefreshState(
        isRefreshing.value == true,
        {
            viewModel.refreshCatalog()
        },
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
            .background(background)
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = gridState,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp
                ),
            content = {
                items(catalog.value?.threads?.count() ?: 0) { index ->
                    Thread(
                        viewModel = viewModel,
                        thread = catalog.value?.threads?.get(index),
                        navController = navController
                    )
                }
            }
        )
        PullRefreshIndicator(
            isRefreshing.value == true,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun Thread(viewModel: HomeActivityVM, thread: ThreadItem?, navController: NavController) {
    if(thread?.stickied == true || thread?.locked == true) return

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable {
                navController.navigate("${Screen.Thread.name}/${thread?.id}")
                viewModel.refreshThread(thread?.id ?: -1)
            },
        colors = CardDefaults.cardColors(containerColor = white),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        thread?.run {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(this.thumbnailUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Thread Image",
                contentScale = ContentScale.Crop,
                error = {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(thread.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Thread Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(150.dp)
                            .clip(
                                RoundedCornerShape(8.dp, 8.dp, 0.dp, 0.dp)
                            ),
                    )
                },
                modifier = Modifier
                    .height(150.dp)
                    .clip(
                        RoundedCornerShape(8.dp, 8.dp, 0.dp, 0.dp)
                    ),
            )
        }

        thread?.sub?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                modifier = Modifier
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
        }
        Text(
            text = thread?.comment ?: "",
            color = Color.DarkGray,
            fontSize = 14.sp,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 2,
            modifier = Modifier
                .padding(top = 2.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
        )
    }
}