package com.kevinvi.doraibu.app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.kevinvi.anime.ui.AnimeSearchResult
import com.kevinvi.doraibu.app.FavListUiState
import com.kevinvi.doraibu.app.FavViewModel
import com.kevinvi.doraibu.app.navigation.navigateToDetails
import com.kevinvi.ui.model.FavItemUi
import com.kevinvi.ui.Loader

@Composable
fun FavListScreen (
	navController: NavHostController,
	viewModel: FavViewModel = hiltViewModel()
){
	val favListUiState by viewModel.favUiState.collectAsStateWithLifecycle()
	FavListScreen(
		favListUiState = favListUiState,
		navController = navController
	)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavListScreen(
	favListUiState: FavListUiState,
	navController: NavHostController,
){

	Scaffold {
		LazyRow(
			modifier = Modifier
				.fillMaxWidth(),
			contentPadding = PaddingValues(8.dp),
		) {

			when (favListUiState) {
				FavListUiState.Loading -> {
					item {
						Loader(true)
					}
				}

				is FavListUiState.Success -> {
					when {
						favListUiState.list.isNotEmpty() -> {
							favListContent(
								list = favListUiState.list,
								onItemClick = {
									navController.navigateToDetails(it)
								},
							)
						}

						else -> {
							item {
								// FeedListEmptyContent(
								// 	onAddClick = onAddClick,
								// )
							}
						}
					}
				}

			}
		}
	}
}

private fun LazyListScope.favListContent(
	list: List<FavItemUi>,
	onItemClick: (item: FavItemUi) -> Unit,
) {
	items(list) {
		FavListItem(
			item = it,
			onItemClick = onItemClick,
		)
	}
}
