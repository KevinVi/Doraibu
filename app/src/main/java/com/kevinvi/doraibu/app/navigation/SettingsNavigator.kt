package com.kevinvi.doraibu.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kevinvi.doraibu.app.settings.SettingsScreen

const val SETTINGS_ROUTE = "settings"

fun NavController.navigateToSettings(navOptions: NavOptions) {
    navigate(SETTINGS_ROUTE, navOptions)
}

fun NavGraphBuilder.addSettingsRoute(navController: NavHostController) {
    composable(SETTINGS_ROUTE) { SettingsScreen(navController) }
}
