package com.kevinvi.doraibu.app.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.kevinvi.doraibu.app.FavListUiState
import com.kevinvi.doraibu.app.FavViewModel
import com.kevinvi.doraibu.app.navigation.navigateToDetails
import com.kevinvi.ui.Dimens.BIGGEST_SPACING
import com.kevinvi.ui.Dimens.BIG_SPACING
import com.kevinvi.ui.Dimens.NORMAL_SPACING
import com.kevinvi.ui.Loader
import com.kevinvi.ui.model.FavItemUi

@Composable
fun FavListScreen(
	navController: NavHostController,
	viewModel: FavViewModel = hiltViewModel(),
) {
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
		LazyVerticalStaggeredGrid(
			columns = StaggeredGridCells.Fixed(2),
			verticalItemSpacing = NORMAL_SPACING,
			reverseLayout = false,
			horizontalArrangement = Arrangement.spacedBy(NORMAL_SPACING),
			contentPadding = PaddingValues(
				start = BIG_SPACING,
				end = BIG_SPACING,
				bottom = BIG_SPACING,
			),
			modifier = Modifier.fillMaxSize(),
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

							items(
								count = favListUiState.list.size,
							) { index ->

								FavListItem(
									item = favListUiState.list[index],
									onItemClick = {navController.navigateToDetails(favListUiState.list[index])},
								)

							}
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



