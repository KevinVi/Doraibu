package com.kevinvi.doraibu.app.navigation

import androidx.annotation.StringRes
import com.kevinvi.doraibu.R
import com.kevinvi.ui.Icon
import com.kevinvi.ui.Icon.DrawableResIcon
import com.kevinvi.ui.R.drawable.home
import com.kevinvi.ui.R.drawable.search
import com.kevinvi.ui.R.drawable.settings

sealed class BottomNavigationScreen(
	val route: String,
	@StringRes val nameResourceId: Int,
	val selectedIcon: Icon,
	val unselectedIcon: Icon = selectedIcon,
) {

	data object Home : BottomNavigationScreen(
		route = "$BASE_FAV_ROUTE",
		nameResourceId = R.string.home,
		selectedIcon = DrawableResIcon(home)
	)
	data object Search : BottomNavigationScreen(
		route = "$BASE_HOME_ROUTE",
		nameResourceId = R.string.search,
		selectedIcon = DrawableResIcon(search)
	)



	data object Settings : BottomNavigationScreen(
		route = "",
		nameResourceId = R.string.settings,
		selectedIcon = DrawableResIcon(settings)
	)
}