package com.link.stinkies.layout.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.link.stinkies.R
import com.link.stinkies.layout.activity.home.Screen
import com.link.stinkies.model.biz.Catalog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StinkiesAppBar(
    currentScreen: Screen,
    navigateUp: () -> Unit,
    refreshCatalog: () -> Unit,
    refreshThread: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            val title = if (currentScreen.base) {
                stringResource(R.string.app_bar_title)
            } else {
                stringResource(R.string.app_bar_view_post)
            } 

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(title == stringResource(R.string.app_bar_title)) {
                    Image(
                        painter = painterResource(R.drawable.link_app_bar),
                        contentDescription = "App Bar Icon",
                        modifier = Modifier
                            .height(36.dp)
                            .padding(end = 2.dp)
                    )
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(bottom = 1.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ),
        modifier = modifier,
        navigationIcon = {
            if (!currentScreen.base) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            if (currentScreen == Screen.Biz) {
                IconButton(onClick = refreshCatalog) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            } else if (currentScreen == Screen.Thread) {
                IconButton(onClick = refreshThread) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        }
    )
}
