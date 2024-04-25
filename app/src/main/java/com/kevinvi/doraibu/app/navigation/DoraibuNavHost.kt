package com.kevinvi.doraibu.app.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun DoraibuNavHost(
	navController: NavHostController,
	startScreen: BottomNavigationScreen,
	innerPadding: PaddingValues,
) {
	NavHost(
		navController = navController,
		startDestination = startScreen.route,
		modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
	) {
		addPopulareRoute(navController)
		addFavRoute(navController)
		addSearchRoute(navController)
		addSettingsRoute(navController)
	}
}