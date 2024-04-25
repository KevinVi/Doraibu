package com.kevinvi.doraibu.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kevinvi.anime.ui.AnimeDetailItemUi
import com.kevinvi.anime.ui.AnimeItemUi
import com.kevinvi.anime.ui.AssetParamType
import com.kevinvi.common.navigation.navigationBottomBar
import com.kevinvi.doraibu.app.ui.DetailItemUi
import com.kevinvi.doraibu.app.ui.FavListScreen
import com.kevinvi.scan.ui.AssetParamTypeScan
import com.kevinvi.scan.mapper.ScanItemMapper
import com.kevinvi.scan.ui.ScanItemDataUi

const val BASE_FAV_ROUTE = "fav"

private const val FAV_ROUTE = "fav"
private const val FAV_LIST_ROUTE = "list"
private const val FAV_DETAIL_ANIME_ROUTE = "detail_anime"
private const val FAV_DETAIL_SCAN_ROUTE = "detail_scan"
private const val FAV_DETAIL_TOME_ROUTE = "detail_tome"
private const val FAV_LIST_ARG = "type"
private const val FAV_DETAILS_ARG = "id"



fun NavController.navigateToFav(navOptions: NavOptions) {
	navigate(FAV_ROUTE, navOptions)
}


fun NavGraphBuilder.addFavRoute(navController: NavHostController) {
	navigationBottomBar(
		startDestination = "$BASE_FAV_ROUTE/$FAV_ROUTE",
		route = BASE_FAV_ROUTE,
	) {
		composable("$BASE_FAV_ROUTE/$FAV_ROUTE") {
			FavListScreen(navController)
		}



		composable(
			"$FAV_DETAIL_ANIME_ROUTE/{data}",
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
			"$FAV_DETAIL_SCAN_ROUTE/{data}",
			arguments = listOf(
				navArgument("data") {
					type = AssetParamTypeScan()
				},
			),
		) {
			it.arguments?.getParcelable<ScanItemDataUi>("data")?.let { scanItem ->
				DetailItemUi(
					ScanItemMapper.mapToDetail(scanItem),
					onBackClick = {
						navController.navigateUp()
					},
					navController,
				)
			}
		}

		// composable(
		// 	route = "$FAV_LIST_ROUTE/{$FAV_LIST_ARG}",
		// 	arguments = listOf(
		// 		navArgument(FAV_LIST_ARG) {
		// 			type = NavType.StringType
		// 		},
		// 	),
		// ) {
		// 	FavListScreen(navController)
	}
}

