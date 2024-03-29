package com.kevinvi.doraibu.app.ui

import android.util.Log
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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kevinvi.common.extension.takeIfNotNullOrBlank
import com.kevinvi.doraibu.app.DetailViewModel
import com.kevinvi.ui.components.ExpandableMangaDescription
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
	LaunchedEffect(key1 = Unit) {
		viewModel.getDetail(item)
	}
	val itemData by viewModel.stateData.collectAsStateWithLifecycle()
	var isFavorite by remember { mutableStateOf(itemData.item.isFav) }

	var sliderPosition by remember { mutableFloatStateOf(0f) }
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(text = itemData.item.type)
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
								itemData.item.isFav = true
								viewModel.saveFav(
									itemData.item
								)
							} else {
								viewModel.deleteFav(itemData.item.id)
								isFavorite = false
								itemData.item.isFav = false
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

			itemData.item.imageUrl.takeIfNotNullOrBlank()?.let {
				AsyncImage(
					model = it,
					contentDescription = null,
					contentScale = ContentScale.Crop,
					modifier = Modifier.aspectRatio(16f / 9f)
				)
			}
			itemData.item.title.takeIfNotNullOrBlank()?.let {
				Text(text = it, Modifier.padding(10.dp))
			}

			itemData.item.description.takeIfNotNullOrBlank()?.let {
				ExpandableMangaDescription(false, it)
			}

			itemData.item.lastEntry.toString().takeIfNotNullOrBlank()?.let {
				Text(text = it, Modifier.padding(10.dp))
			}
			Log.d("TAG", "DetailContent: $sliderPosition && ${itemData.item.progression.toFloat()}")
			if (itemData.item.lastEntry > 0) {
				Column {
					Log.d("TAG", "DetailContent: ${itemData.item.lastEntry.toFloat()}")
					Slider(
						value = sliderPosition,
						onValueChange = {
							sliderPosition = it
							viewModel.saveProgression(itemData.item.id, it.toInt())
						},
						colors = SliderDefaults.colors(
							thumbColor = MaterialTheme.colorScheme.secondary,
							activeTrackColor = MaterialTheme.colorScheme.secondary,
							inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer,
						),
						steps = itemData.item.lastEntry,
						valueRange = 0f..itemData.item.lastEntry.toFloat()
					)
					Text(text = sliderPosition.toInt().toString())
				}
			}

		}

	}
}

@Preview
@Composable
fun DetailItemComposable() {

	DetailContent(
		FavItemUi(
			id = "10",
			title = "One piece",
			description = "un mec qui a un équipage et qui veut devenir roi des pirates",
			createdAt = "date 1 ",
			updatedAt = "data 2 ",
			imageUrl = "https://uploads.mangadex.org/covers/68112dc1-2b80-4f20-beb8-2f2a8716a430/c3f43d5a-83c4-44bd-a117-b247019329b2.jpg",
		),
		onBackClick = {},
	)
}