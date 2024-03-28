package com.kevinvi.doraibu.app.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kevinvi.common.TypeUi
import com.kevinvi.common.utils.IdFavoriteUtils
import com.kevinvi.doraibu.app.DetailViewModel
import com.kevinvi.ui.model.FavItemUi

@Composable
fun DetailItemUi(
	item: FavItemUi,
	onBackClick: () -> Unit,
) {
	DetailContent(
		item = item,
		onBackClick = onBackClick
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailContent(
	item: FavItemUi,
	onBackClick: () -> Unit,
	viewModel: DetailViewModel = hiltViewModel(),
) {
	var isFavorite by remember { mutableStateOf(false) }
	LaunchedEffect(key1 = Unit) {
		viewModel.getDetail(item)
	}
	val item by viewModel.stateData.collectAsStateWithLifecycle()
}