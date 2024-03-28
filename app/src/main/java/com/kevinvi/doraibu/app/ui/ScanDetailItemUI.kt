package com.kevinvi.doraibu.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kevinvi.common.TypeUi
import com.kevinvi.common.utils.IdFavoriteUtils
import com.kevinvi.common.extension.empty
import com.kevinvi.common.extension.takeIfNotNullOrBlank
import com.kevinvi.doraibu.app.MainActivityViewModel
import com.kevinvi.ui.model.FavItemUi
import com.kevinvi.scan.ui.ScanItemDataUi
import com.kevinvi.ui.Loader

@Composable
fun ScanDetailItemUi(
	item: ScanItemDataUi,
	onBackClick: () -> Unit,
) {
	ScanDetailContent(
		item = item,
		onBackClick = onBackClick
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScanDetailContent(
	item: ScanItemDataUi,
	onBackClick: () -> Unit,
	viewModel: MainActivityViewModel = hiltViewModel(),
) {

	var isFavorite by remember { mutableStateOf(false) }


	LaunchedEffect(key1 = Unit) {
		viewModel.favRepository.getById(IdFavoriteUtils().buildId(item.id, TypeUi.SCAN.name))
	}


	var isFaved = viewModel.favRepository.getById(IdFavoriteUtils().buildId(item.id, TypeUi.SCAN.name))
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(text = "Scan")
				},
				navigationIcon = {
					IconButton(onClick = onBackClick) {
						Icon(
							imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
							contentDescription = null,
						)
					}
				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = MaterialTheme.colorScheme.surface,
					navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
				),
				actions = {
					IconButton(
						onClick = {
							if (!isFavorite) {
								isFavorite = true
								viewModel.saveFav(
									FavItemUi(
										id = item.id + ";scan",
										type = "scan",
										title = item.title,
										description = item.description,
										author = String.empty,
										imageUrl = item.image,
										language = String.empty,
										createdAt = item.createdAt,
										updatedAt = item.updatedAt,
										progression = 0,
										lastEntry = 15,
										linked = "",
									)
								)
							} else {
								viewModel.deleteFav(item.id + ";scan")
								isFavorite = false
							}
						},
					) {
						Icon(
							imageVector = when (isFavorite) {
								true -> Icons.Rounded.Bookmark
								else -> Icons.Rounded.BookmarkBorder
							},
							contentDescription = null,
						)
					}
				},
			)
		},
	) { paddingValues ->
		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(rememberScrollState())
				.padding(horizontal = 16.dp),
		) {

			Spacer(Modifier.height(paddingValues.calculateTopPadding()))

			item.image.takeIfNotNullOrBlank()?.let {
				AsyncImage(
					model = it,
					contentDescription = null,
					contentScale = ContentScale.Crop,
					modifier = Modifier.aspectRatio(16f / 9f)
				)
			}
			item.title.takeIfNotNullOrBlank()?.let {
				Text(text = it, Modifier.padding(10.dp))
			}
			item.description.takeIfNotNullOrBlank()?.let {
				Text(text = it, Modifier.padding(10.dp))
			}

			Loader(true)

		}

	}
}

@Preview
@Composable
fun ScanDetailItemComposable() {

	ScanDetailContent(
		ScanItemDataUi(
			id = "10",
			title = "One piece",
			description = "un mec qui a un équipage et qui veut devenir roi des pirates",
			createdAt = "date 1 ",
			updatedAt = "data 2 ",
			image = "https://uploads.mangadex.org/covers/68112dc1-2b80-4f20-beb8-2f2a8716a430/c3f43d5a-83c4-44bd-a117-b247019329b2.jpg",
		),
		onBackClick = {},
	)
}