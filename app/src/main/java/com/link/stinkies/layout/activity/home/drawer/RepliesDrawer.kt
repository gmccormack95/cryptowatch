@file:OptIn(ExperimentalMaterial3Api::class)

package com.link.stinkies.layout.activity.home.drawer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.link.stinkies.R
import com.link.stinkies.layout.activity.home.thread.post.Post
import com.link.stinkies.viewmodel.activity.HomeActivityVM
import com.link.stinkies.viewmodel.activity.home.replies.RepliesDrawerVM
import kotlinx.coroutines.launch

@Composable
fun RepliesDrawer(viewModel: HomeActivityVM, drawerState: DrawerState, content: @Composable () -> Unit) {
    val repliesDrawerVM = viewModel.threadLayoutVM.repliesDrawerVM
    val replies = repliesDrawerVM.replies.observeAsState()
    val scope = rememberCoroutineScope()

    BackHandler(
        enabled = drawerState.isOpen,
        onBack = {
            scope.launch {
                drawerState.close()
            }
        }
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalDrawer(
            drawerState = drawerState,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    // under the hood, drawerContent is wrapped in a Column, but it would be under the Rtl layout
                    // so we create new column filling max width under the Ltr layout
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Replies",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier
                                        .padding(bottom = 1.dp)
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = Color.White
                            ),
                            actions = {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Back",
                                        tint = Color.White
                                    )
                                }
                            }
                        )
                        LazyColumn(
                            state = rememberLazyListState(),
                            content = {
                                items(replies.value?.count() ?: 0) { index ->
                                    Post(
                                        viewModel = viewModel,
                                        post = replies.value?.get(index),
                                        drawerState = drawerState,
                                        modifier = Modifier
                                            .then(
                                                when (index) {
                                                    0 -> {
                                                        Modifier.padding(bottom = 4.dp)
                                                    }
                                                    (replies.value?.size ?: 0) -1 -> {
                                                        Modifier.padding(start = 14.dp, bottom = 16.dp)
                                                    }
                                                    else -> {
                                                        Modifier.padding(start = 14.dp, bottom = 4.dp)
                                                    }
                                                }
                                            )
                                    )
                                }
                            },
                            modifier = Modifier
                                .padding(
                                    start = 8.dp,
                                    end = 8.dp,
                                )
                                .fillMaxHeight()
                        )
                    }
                }
            },
            content = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    content()
                }
            },
        )
    }
}