package com.kevinvi.doraibu.app.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material3.Icon
import com.kevinvi.doraibu.R
import com.kevinvi.ui.Icon
import com.kevinvi.ui.Icon.DrawableResIcon
import com.kevinvi.ui.R.drawable.baseline_bookmark_border_24
import com.kevinvi.ui.R.drawable.baseline_star_outline_24
import com.kevinvi.ui.R.drawable.home
import com.kevinvi.ui.R.drawable.search
import com.kevinvi.ui.R.drawable.settings

sealed class BottomNavigationScreen(
	val route: String,
	@StringRes val nameResourceId: Int,
	val selectedIcon: Icon,
	val unselectedIcon: Icon = selectedIcon,
) {

	data object Populare : BottomNavigationScreen(
		route = "$BASE_POPULARE_ROUTE",
		nameResourceId = R.string.populare,
		selectedIcon = DrawableResIcon(baseline_star_outline_24)
	)

	data object Home : BottomNavigationScreen(
		route = "$BASE_FAV_ROUTE",
		nameResourceId = R.string.home,
		selectedIcon = DrawableResIcon(baseline_bookmark_border_24)
	)
	data object Search : BottomNavigationScreen(
		route = "$BASE_SEARCH_ROUTE",
		nameResourceId = R.string.search,
		selectedIcon = DrawableResIcon(search)
	)

	data object Settings : BottomNavigationScreen(
		route = SETTINGS_ROUTE,
		nameResourceId = R.string.settings,
		selectedIcon = DrawableResIcon(settings)
	)
}