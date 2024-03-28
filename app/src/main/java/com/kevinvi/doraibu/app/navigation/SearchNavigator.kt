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

const val BASE_SEARCH_ROUTE = "search"

private const val SEARCH_ROUTE = "search"
private const val SEARCH_LIST_ROUTE = "list"
private const val SEARCH_DETAIL_ANIME_ROUTE = "detail_anime"
private const val SEARCH_DETAIL_SCAN_ROUTE = "detail_scan"
private const val SEARCH_DETAIL_TOME_ROUTE = "detail_tome"
private const val SEARCH_LIST_ARG = "type"
private const val SEARCH_DETAILS_ARG = "id"

internal class SearchDetailsArg(val id: String) {
	constructor(savedStateHandle: SavedStateHandle) :
		this(
			URLDecoder.decode(
				checkNotNull(savedStateHandle[SEARCH_DETAILS_ARG]),
				URL_ENCODING,
			),
		)
}

fun NavController.navigateToSearch(navOptions: NavOptions) {
	navigate(SEARCH_ROUTE, navOptions)
}

fun NavController.navigateToAnimeDetails(item: AnimeItemUi) {
	val json = Json.encodeToJsonElement(item)
	navigate(
		"$SEARCH_DETAIL_ANIME_ROUTE/${URLEncoder.encode(json.toString(), URL_ENCODING)}",
	) {
		launchSingleTop = true
	}
}

fun NavController.navigateToScanDetails(item: ScanItemDataUi) {
	val json = Json.encodeToJsonElement(item)
	navigate(
		"$SEARCH_DETAIL_SCAN_ROUTE/${URLEncoder.encode(json.toString(), URL_ENCODING)}",
	) {
		launchSingleTop = true
	}
}

fun NavGraphBuilder.addSearchRoute(navController: NavHostController) {
	navigationBottomBar(
		startDestination = "$BASE_SEARCH_ROUTE/$SEARCH_ROUTE",
		route = BASE_SEARCH_ROUTE,
	) {
		composable("$BASE_SEARCH_ROUTE/$SEARCH_ROUTE") {
			MainScreen(navController)
		}



		composable(
			"$SEARCH_DETAIL_ANIME_ROUTE/{data}",
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
			"$SEARCH_DETAIL_SCAN_ROUTE/{data}",
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
		// 	route = "$SEARCH_LIST_ROUTE/{$SEARCH_LIST_ARG}",
		// 	arguments = listOf(
		// 		navArgument(SEARCH_LIST_ARG) {
		// 			type = NavType.StringType
		// 		},
		// 	),
		// ) {
		// 	SearchListScreen(navController)
	}
}

