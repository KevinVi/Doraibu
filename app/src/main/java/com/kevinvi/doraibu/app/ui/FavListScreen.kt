package com.kevinvi.doraibu.app.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.kevinvi.doraibu.app.FavListUiState
import com.kevinvi.doraibu.app.FavViewModel
import com.kevinvi.doraibu.app.navigation.navigateToDetails
import com.kevinvi.ui.Dimens.BIG_SPACING
import com.kevinvi.ui.Dimens.NORMAL_SPACING
import com.kevinvi.ui.Loader
import com.kevinvi.ui.model.FavItemUi
import okhttp3.internal.filterList
import java.util.Locale

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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavListScreen(
	favListUiState: FavListUiState,
	navController: NavHostController,
) {
	var isGridOn by remember {
		mutableStateOf(true)
	}
	var text by rememberSaveable { mutableStateOf("") }
	var favItems by rememberSaveable { mutableStateOf(emptyList<FavItemUi>()) }
	var favItemsComplete by rememberSaveable { mutableStateOf(emptyList<FavItemUi>()) }
	var searchLauched by rememberSaveable { mutableStateOf(false) }
	val keyboardController = LocalSoftwareKeyboardController.current
	val focusManager = LocalFocusManager.current
	Scaffold(
		floatingActionButton = {
			FloatingActionButton(
				shape = MaterialTheme.shapes.medium,
				onClick = {
					isGridOn = !isGridOn
				},
			) {
				if (isGridOn) {
					Icon(imageVector = Icons.Default.GridView, contentDescription = "Grid")
				} else {
					Icon(imageVector = Icons.AutoMirrored.Default.List, contentDescription = "List")
				}
			}
		},
	) {
		Column {
			SearchBar(
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp),
				query = text,
				onQueryChange = {
					text = it
				},
				onSearch = {
					searchLauched = true
					//viewModel.search(text)
					focusManager.clearFocus()
					keyboardController?.hide()
				},
				active = false,
				onActiveChange = {
				},
				placeholder = {
					Text(text = "Rechercher")
				},
				leadingIcon = {
					Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
				},
				trailingIcon = {
					Icon(
						modifier = Modifier.clickable {
							if (text.isNotEmpty()) {
								text = ""
							}
						},
						imageVector = Icons.Default.Close,
						contentDescription = "Close item"
					)
				}
			) {
			}

			when (favListUiState) {
				FavListUiState.Loading -> {
					Loader(true)
				}

				is FavListUiState.Success -> {
					when {
						favListUiState.list.isNotEmpty() -> {
							favItemsComplete = favListUiState.list


							if (isGridOn) {
								DisplayGrid(list = favItemsComplete, navController)
							} else {
								DisplayList(list = favItemsComplete, navController)
							}
						}

						else -> {
							Text(text = "no fav")
						}
					}
				}

			}
		}
	}

}

@Composable
fun DisplayGrid(
	list: List<FavItemUi>,

	navController: NavHostController,
) {
	LazyVerticalStaggeredGrid(
		columns = StaggeredGridCells.Fixed(2),
		verticalItemSpacing = NORMAL_SPACING,
		reverseLayout = false,
		horizontalArrangement = Arrangement.End,
		contentPadding = PaddingValues(
			start = BIG_SPACING,
			end = BIG_SPACING,
			bottom = BIG_SPACING,
		),
		modifier = Modifier.fillMaxSize(),
	) {
		items(
			count = list.size,
		) { index ->

			FavListItem(
				item = list[index],
				onItemClick = { navController.navigateToDetails(list[index]) },
			)

		}
	}
}

@Composable
fun DisplayList(
	list: List<FavItemUi>,

	navController: NavHostController,
) {
	LazyVerticalStaggeredGrid(
		columns = StaggeredGridCells.Fixed(1),
		verticalItemSpacing = NORMAL_SPACING,
		reverseLayout = false,
		horizontalArrangement = Arrangement.End,
		contentPadding = PaddingValues(
			start = BIG_SPACING,
			end = BIG_SPACING,
			bottom = BIG_SPACING,
		),
		modifier = Modifier.fillMaxSize(),
	) {
		items(
			list,
			key = { it.id }
		) { itemUi ->
			FavListItem(
				item = itemUi,
				onItemClick = { navController.navigateToDetails(itemUi) },
			)
		}
	}
}



