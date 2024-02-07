@file:OptIn(ExperimentalMaterialApi::class, ExperimentalGlideComposeApi::class,
    ExperimentalMaterialApi::class, ExperimentalMaterialApi::class
)

package com.link.stinkies.layout.activity.home.thread

import android.util.Log.d
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DrawerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.link.stinkies.layout.activity.home.simpleVerticalScrollbar
import com.link.stinkies.viewmodel.activity.HomeActivityVM
import com.link.stinkies.layout.activity.home.thread.post.Post
import com.link.stinkies.ui.theme.background
import kotlinx.coroutines.launch

@Composable
fun ThreadLayout(viewModel: HomeActivityVM, threadId: Int, drawerState: DrawerState) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val thread = viewModel.threadLayoutVM.thread.observeAsState()
    val isRefreshing = viewModel.threadLayoutVM.loading.observeAsState()
    val pullRefreshState = rememberPullRefreshState(
        isRefreshing.value == true,
        {
            viewModel.refreshThread(threadId)
        }
    )

    BackHandler(
        enabled = drawerState.isOpen,
        onBack = {
            if (drawerState.isOpen) {
                scope.launch {
                    drawerState.close()
                }
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            state = listState,
            content = {
                items(thread.value?.posts?.count() ?: 0) { index ->
                    Post(
                        viewModel = viewModel,
                        post = thread.value?.posts?.get(index),
                        drawerState = drawerState,
                        modifier = if(index == (thread.value?.posts?.size?.minus(1)))
                            Modifier.padding(
                                bottom = 8.dp,
                                start = 8.dp,
                                end = 8.dp
                            )
                        else
                            Modifier
                                .padding(
                                    start = 8.dp,
                                    end = 8.dp
                                )
                    )
                }
            },
            modifier = Modifier
                .fillMaxHeight()
                .simpleVerticalScrollbar(state = listState)
        )
        PullRefreshIndicator(
            isRefreshing.value == true,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}