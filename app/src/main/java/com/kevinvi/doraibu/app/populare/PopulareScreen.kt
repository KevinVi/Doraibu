package com.kevinvi.doraibu.app.populare

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kevinvi.doraibu.app.navigation.navigateToDetails
import com.kevinvi.doraibu.app.ui.FavListItem
import com.kevinvi.scan.mapper.ScanItemMapper
import com.kevinvi.scan.ui.ScanItemDataUi
import com.kevinvi.ui.Dimens
import com.kevinvi.ui.Loader

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PopulareScreen(
	navController: NavHostController = rememberNavController(),
) {
	val viewModel: PopulareViewModel = hiltViewModel()
	val populare by viewModel.stateData.collectAsStateWithLifecycle()
	viewModel.populare()
	if (populare.isScanLoading) {
		Loader(true)
	} else {
		DisplayGrid(list = populare.list, navController = navController)
	}
}

@Composable
fun DisplayGrid(
	list: List<ScanItemDataUi>,

	navController: NavHostController,
) {
	LazyVerticalStaggeredGrid(
		columns = StaggeredGridCells.Fixed(3),
		verticalItemSpacing = Dimens.NORMAL_SPACING,
		reverseLayout = false,
		horizontalArrangement = Arrangement.End,
		contentPadding = PaddingValues(
			start = Dimens.BIG_SPACING,
			end = Dimens.BIG_SPACING,
			bottom = Dimens.BIG_SPACING,
		),
		modifier = Modifier.fillMaxSize().statusBarsPadding(),
	) {
		items(
			list,
			key = { it.id }
		) { itemUi ->
			val item = ScanItemMapper.mapToDetail(itemUi)
			FavListItem(
				item = item,
				onItemClick = { navController.navigateToDetails(item) },
				false
			)
		}
	}
}