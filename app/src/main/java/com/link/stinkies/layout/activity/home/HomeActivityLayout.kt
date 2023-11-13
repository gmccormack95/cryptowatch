@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalStdlibApi::class)

package com.link.stinkies.layout.activity.home

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.link.stinkies.R
import com.link.stinkies.layout.activity.home.thread.ThreadLayout
import com.link.stinkies.layout.appbar.StinkiesAppBar
import com.link.stinkies.layout.catalog.CatalogLayout
import com.link.stinkies.layout.charts.ChartLayout
import com.link.stinkies.layout.settings.SettingsLayout
import com.link.stinkies.ui.theme.StinkiesTheme
import com.link.stinkies.viewmodel.activity.HomeActivityVM

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

val items = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = Screen.Home.name
    ),
    BottomNavigationItem(
        title = "Biz",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        route = Screen.Biz.name
    ),
    /*BottomNavigationItem(
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        route = Screen.Settings.name
    ),*/
)

enum class Screen(val base: Boolean = false, val route: String) {
    Home(base = true, route = "Home"),
    Biz(base = true, route = "Biz"),
    Settings(base = true, route = "Settings"),
    Thread(route = "Thread/{threadId}")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeActivityLayout(viewModel: HomeActivityVM, navController: NavHostController = rememberNavController()) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = Screen.values().firstOrNull {
        backStackEntry?.destination?.route?.contains(it.name) == true
    } ?: Screen.Home

    val currentDestination = backStackEntry?.destination

    StinkiesTheme(
        dynamicColor = false
    ) {
        // A surface container using the 'background' color from the theme
        Scaffold (
            topBar = {
                StinkiesAppBar(
                    currentScreen = currentScreen,
                    navigateUp = { navController.navigateUp() },
                    refreshCatalog = { viewModel.refreshCatalog() },
                    refreshThread = { viewModel.refreshCurrentThread() }
                )
            },
            bottomBar = {
                NavigationBar (
                ) {
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == item.route} == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        //saveState = true
                                    }
                                    //launchSingleTop = true
                                }
                            },
                            label = {
                                Text(text = item.title)
                            },
                            alwaysShowLabel = false,
                            icon = {
                                Icon(
                                    imageVector = if (currentDestination?.hierarchy?.any { it.route == item.route} == true) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(
                    route = Screen.Home.route,
                ) {
                    ChartLayout(viewModel.chartLayoutVM)
                }
                composable(
                    route = Screen.Biz.route,
                ) {
                    CatalogLayout(viewModel, navController)
                }
                composable(route = Screen.Settings.route) {
                    SettingsLayout(viewModel)
                }
                composable(
                    route = Screen.Thread.route,
                    arguments = listOf(
                        navArgument("threadId") {
                            type = NavType.IntType
                        }
                    ),
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    },
                    popEnterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    },
                    popExitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }
                ) { navBackStackEntry ->
                    val threadId = navBackStackEntry.arguments?.getInt("threadId") ?: -1
                    ThreadLayout(viewModel, threadId)
                }
            }
        }
    }
}