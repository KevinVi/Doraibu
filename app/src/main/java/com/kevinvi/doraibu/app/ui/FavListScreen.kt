package com.kevinvi.doraibu.app.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.kevinvi.common.extension.empty
import com.kevinvi.doraibu.app.FavListUiState
import com.kevinvi.doraibu.app.FavViewModel
import com.kevinvi.doraibu.app.navigation.navigateToDetails
import com.kevinvi.doraibu.app.store.FavDataStore
import com.kevinvi.ui.Dimens.BIG_SPACING
import com.kevinvi.ui.Dimens.NORMAL_SPACING
import com.kevinvi.ui.Loader
import com.kevinvi.ui.model.FavItemUi
import com.kevinvi.ui.noInset

@Composable
fun FavListScreen(
	navController: NavHostController,
	viewModel: FavViewModel = hiltViewModel(),
) {
	val favListUiState by viewModel.favUiState.collectAsStateWithLifecycle()
	val gridDisplay = viewModel.getDisplay.collectAsStateWithLifecycle(
		initialValue = true,
	)
	val gridGridWdth = viewModel.getGridWidth.collectAsStateWithLifecycle(
		initialValue = 2,
	)
	val typeElement = viewModel.getTypeElement.collectAsStateWithLifecycle(
		initialValue = 2,
	)

	LaunchedEffect(key1 = typeElement.value) {
		viewModel.filterElement(typeElement.value)

	}
	Log.d("TAG", "FavListScreen: init $gridDisplay")
	FavListScreen(
		favListUiState = favListUiState,
		navController = navController,
		gridDisplay = gridDisplay,
		gridDisplayWidth = gridGridWdth,
		typeElement = typeElement,
		onclick = {
			FavDataStore.saveListPosition(navController.context, !gridDisplay.value)
		},
		viewModel = viewModel

	)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavListScreen(
	favListUiState: FavListUiState,
	navController: NavHostController,
	gridDisplay: State<Boolean>,
	gridDisplayWidth: State<Int>,
	typeElement: State<Int>,
	onclick: () -> Unit,
	viewModel: FavViewModel,
) {
	var showSheet by remember { mutableStateOf(false) }
	val search by viewModel.stateData.collectAsStateWithLifecycle()
	var text by rememberSaveable { mutableStateOf("") }
	var favItems by rememberSaveable { mutableStateOf(emptyList<FavItemUi>()) }
	var favItemsComplete by rememberSaveable { mutableStateOf(emptyList<FavItemUi>()) }
	var searchLauched by rememberSaveable { mutableStateOf(false) }
	val keyboardController = LocalSoftwareKeyboardController.current
	val focusManager = LocalFocusManager.current
	Scaffold(
		contentWindowInsets = WindowInsets.noInset,
		floatingActionButton = {
			FloatingActionButton(

				shape = MaterialTheme.shapes.medium,
				onClick = {
					showSheet = true
					//onclick()
				},
			) {
				Icon(imageVector = Icons.Default.FilterList, contentDescription = "Grid")
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
					viewModel.search(text)
				},
				onSearch = {
					searchLauched = true
					viewModel.search(text)
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
								text = String.empty
								viewModel.search(String.empty)
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
						search.list.isNotEmpty() -> {

							//viewModel.filterElement(typeElement.value)
							favItemsComplete = search.list


							if (gridDisplay.value) {
								DisplayGrid(list = favItemsComplete, navController, gridDisplayWidth.value)
							} else {
								DisplayList(list = favItemsComplete, navController)
							}
						}

						else -> {
							Text(
								text = "Vous n'avez pas encore de favoris",
								textAlign = TextAlign.Center,
								modifier = Modifier
									.fillMaxWidth()
									.fillMaxHeight()
									.wrapContentHeight()
							)
						}
					}
				}

			}

			if (showSheet) {
				BottomSheet(
					context = navController.context,
					gridDisplay = gridDisplay,
					onDismiss = { showSheet = false },
					gridDisplayWidth = gridDisplayWidth,
					typeElement = typeElement
				)
			}
		}
	}

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit, context: Context, gridDisplay: State<Boolean>, gridDisplayWidth: State<Int>, typeElement: State<Int>) {
	val modalBottomSheetState = rememberModalBottomSheetState()

	ModalBottomSheet(
		onDismissRequest = { onDismiss() },
		sheetState = modalBottomSheetState,
		dragHandle = { BottomSheetDefaults.DragHandle() },
	) {
		RowDisplay(context, gridDisplay, gridDisplayWidth, typeElement)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowDisplay(
	context: Context,
	gridDisplay: State<Boolean>,
	gridDisplayWidth: State<Int>,
	typeElement: State<Int>,
) {
	val radioOptions = listOf("Tous", "Scan", "Anime")
	val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[typeElement.value]) }
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(8.dp),
	) {
		radioOptions.forEachIndexed { index, text ->
			Row(
				Modifier
					.fillMaxWidth()
					.selectable(
						selected = (text == selectedOption),
						onClick = {
							onOptionSelected(text)
							FavDataStore.saveElementDisplay(context, index)
						}
					),
				verticalAlignment = Alignment.CenterVertically
			) {
				RadioButton(
					selected = (text == selectedOption),
					onClick = {
						onOptionSelected(text)
						FavDataStore.saveElementDisplay(context, index)
					}
				)
				Text(
					text = text,
					modifier = Modifier.padding(start = 16.dp),
					textAlign = TextAlign.Center
				)
			}
		}
	}

	val options = listOf("Liste", "Grille")
	SingleChoiceSegmentedButtonRow(
		Modifier
			.fillMaxWidth()
			.padding(18.dp)
	) {
		SegmentedButton(
			shape = SegmentedButtonDefaults.itemShape(index = 0, count = options.size),
			onClick = {
				FavDataStore.saveListPosition(context, !gridDisplay.value)
			},
			selected = !gridDisplay.value,
			icon = {
				SegmentedButtonDefaults.Icon(
					active = true,
					activeContent = { Icon(Icons.AutoMirrored.Filled.ViewList, contentDescription = null) }
				)
			}
		) {
			Text(options[0])
		}
		SegmentedButton(
			shape = SegmentedButtonDefaults.itemShape(index = 1, count = options.size),
			onClick = {
				FavDataStore.saveListPosition(context, !gridDisplay.value)
			},
			selected = gridDisplay.value,
			icon = {
				SegmentedButtonDefaults.Icon(
					active = true,
					activeContent = { Icon(Icons.Filled.Apps, contentDescription = null) }
				)
			}
		) {
			Text(options[1])
		}
	}

	var gridSize by remember { mutableStateOf(gridDisplayWidth.value) }
	Row(
		modifier = Modifier
			.fillMaxWidth()
	) {

		Text(text = "Taille de grille $gridSize", Modifier.weight(1f), textAlign = TextAlign.Center)
		Slider(
			modifier = Modifier.weight(3f),
			value = gridDisplayWidth.value.toFloat(),
			onValueChange = {
				gridSize = it.toInt()
				FavDataStore.saveGridWith(context, it.toInt())
			},
			colors = SliderDefaults.colors(
				thumbColor = MaterialTheme.colorScheme.secondary,
				activeTrackColor = MaterialTheme.colorScheme.secondary,
				inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
			),
			steps = 2,
			valueRange = 2f..5f,
			enabled = gridDisplay.value
		)
	}
}

@Composable
fun DisplayGrid(
	list: List<FavItemUi>,

	navController: NavHostController,
	size: Int,
) {
	LazyVerticalStaggeredGrid(
		columns = StaggeredGridCells.Fixed(size),
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
			FavSingleUi(
				item = itemUi,
				onItemClick = { navController.navigateToDetails(itemUi) },
			)
		}
	}
}



