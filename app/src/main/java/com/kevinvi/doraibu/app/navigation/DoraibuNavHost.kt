package com.kevinvi.doraibu.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost

@Composable
fun DoraibuNavHost(
	onShowSnackbar: suspend (String, String?) -> Boolean,
	modifier: Modifier = Modifier,
) {
	val navController = appState.navController
	NavHost(
		navController = navController,
		modifier = modifier,
	) {

	}
}