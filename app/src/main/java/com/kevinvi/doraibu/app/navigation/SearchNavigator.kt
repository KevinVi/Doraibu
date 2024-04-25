package com.kevinvi.doraibu.app.navigation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kevinvi.common.navigation.NavigationUtils.URL_ENCODING
import com.kevinvi.common.navigation.navigationBottomBar
import com.kevinvi.doraibu.app.ui.DetailItemUi
import com.kevinvi.doraibu.app.ui.SearchScreen
import com.kevinvi.ui.model.AssetParamTypeDetail
import com.kevinvi.ui.model.FavItemUi
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



fun NavController.navigateToDetails(item: FavItemUi) {
	val json = Json.encodeToJsonElement(item)

	Log.d("TAG", "navigateToDetails: $json")
	navigate(
		"$SEARCH_DETAIL_SCAN_ROUTE/${URLEncoder.encode(json.toString(), URL_ENCODING)}",
	)
}

fun NavGraphBuilder.addSearchRoute(navController: NavHostController) {
	navigationBottomBar(
		startDestination = "$BASE_SEARCH_ROUTE/$SEARCH_ROUTE",
		route = BASE_SEARCH_ROUTE,
	) {
		composable("$BASE_SEARCH_ROUTE/$SEARCH_ROUTE") {
			SearchScreen(navController)
		}
	}
	composable(
		"$SEARCH_DETAIL_SCAN_ROUTE/{data}",
		arguments = listOf(
			navArgument("data") {
				type = AssetParamTypeDetail()
			},
		),
	) {
		it.arguments?.getParcelable<FavItemUi>("data")?.let { item ->
			DetailItemUi(
				item,
				onBackClick = {
					navController.navigateUp()
				},
				navController
			)
		}
	}
}

