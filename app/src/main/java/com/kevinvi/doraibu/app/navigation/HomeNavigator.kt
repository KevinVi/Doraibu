package com.kevinvi.doraibu.app.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kevinvi.anime.ui.AnimeDetailItemUi
import com.kevinvi.anime.ui.AnimeItemUi
import com.kevinvi.anime.ui.AssetParamType
import com.kevinvi.common.navigation.NavigationUtils.URL_ENCODING
import com.kevinvi.common.navigation.navigationBottomBar
import com.kevinvi.doraibu.app.ui.MainScreen
import com.kevinvi.scan.ui.AssetParamTypeScan
import com.kevinvi.doraibu.app.ui.ScanDetailItemUi
import com.kevinvi.scan.ui.ScanItemDataUi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.net.URLDecoder
import java.net.URLEncoder

const val BASE_HOME_ROUTE = "home"

private const val HOME_ROUTE = "home"
private const val HOME_LIST_ROUTE = "list"
private const val HOME_DETAIL_ANIME_ROUTE = "detail_anime"
private const val HOME_DETAIL_SCAN_ROUTE = "detail_scan"
private const val HOME_DETAIL_TOME_ROUTE = "detail_tome"
private const val HOME_LIST_ARG = "type"
private const val HOME_DETAILS_ARG = "id"

internal class HomeDetailsArg(val id: String) {
	constructor(savedStateHandle: SavedStateHandle) :
		this(
			URLDecoder.decode(
				checkNotNull(savedStateHandle[HOME_DETAILS_ARG]),
				URL_ENCODING,
			),
		)
}

fun NavController.navigateToHome(navOptions: NavOptions) {
	navigate(HOME_ROUTE, navOptions)
}

fun NavController.navigateToAnimeDetails(item: AnimeItemUi) {
	val json = Json.encodeToJsonElement(item)
	navigate(
		"$HOME_DETAIL_ANIME_ROUTE/${URLEncoder.encode(json.toString(), URL_ENCODING)}",
	) {
		launchSingleTop = true
	}
}

fun NavController.navigateToScanDetails(item: ScanItemDataUi) {
	val json = Json.encodeToJsonElement(item)
	navigate(
		"$HOME_DETAIL_SCAN_ROUTE/${URLEncoder.encode(json.toString(), URL_ENCODING)}",
	) {
		launchSingleTop = true
	}
}

fun NavGraphBuilder.addHomeRoute(navController: NavHostController) {
	navigationBottomBar(
		startDestination = "$BASE_HOME_ROUTE/$HOME_ROUTE",
		route = BASE_HOME_ROUTE,
	) {
		composable("$BASE_HOME_ROUTE/$HOME_ROUTE") {
			MainScreen(navController)
		}



		composable(
			"$HOME_DETAIL_ANIME_ROUTE/{data}",
			arguments = listOf(
				navArgument("data") {
					type = AssetParamType()
				},
			),
		) {
			it.arguments?.getParcelable<AnimeItemUi>("data")?.let { it1 ->
				AnimeDetailItemUi(
					it1,
					onBackClick = {
						navController.navigateUp()
					},
				)
			}
		}


		composable(
			"$HOME_DETAIL_SCAN_ROUTE/{data}",
			arguments = listOf(
				navArgument("data") {
					type = AssetParamTypeScan()
				},
			),
		) {
			it.arguments?.getParcelable<ScanItemDataUi>("data")?.let { it1 ->
				ScanDetailItemUi(
					it1,
					onBackClick = {
						navController.navigateUp()
					},
				)
			}
		}

		// composable(
		// 	route = "$HOME_LIST_ROUTE/{$HOME_LIST_ARG}",
		// 	arguments = listOf(
		// 		navArgument(HOME_LIST_ARG) {
		// 			type = NavType.StringType
		// 		},
		// 	),
		// ) {
		// 	HomeListScreen(navController)
	}
}

