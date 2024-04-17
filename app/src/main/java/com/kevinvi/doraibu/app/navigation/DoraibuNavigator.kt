package com.kevinvi.doraibu.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.kevinvi.doraibu.app.navigation.BottomNavigationScreen.Home
import com.kevinvi.doraibu.app.navigation.BottomNavigationScreen.Search
import com.kevinvi.doraibu.app.navigation.BottomNavigationScreen.Settings

@Composable
fun rememberDoraibuNavigator(
    navController: NavHostController = rememberNavController(),
) = remember(navController) {
    DoraibuNavigator(navController)
}

class DoraibuNavigator(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentScreen: BottomNavigationScreen
        @Composable get() = when (currentDestination?.route) {
            Home.route -> Home
            Search.route -> Search
            Settings.route -> Settings
            else -> startScreen
        }

    fun navigateToScreen(screen: BottomNavigationScreen) {
        val navOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = false
        }

        when (screen) {
            Home -> navController.navigateToFav(navOptions)
            Search -> navController.navigateToSearch(navOptions)
            Settings -> navController.navigateToSettings(navOptions)
        }
    }

    companion object {
        val destinations = listOf(Home, Search, Settings)

        /*Settings*/
        val startScreen = destinations.first()
    }
}
