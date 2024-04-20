package com.kevinvi.doraibu.app.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material3.Button
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.kevinvi.common.extension.takeIfNotNullOrBlank
import com.kevinvi.doraibu.app.DetailViewModel
import com.kevinvi.ui.Dimens.NORMAL_SPACING
import com.kevinvi.ui.components.ExpandableMangaDescription
import com.kevinvi.ui.components.RepeatingButton
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun DetailContent(
	item: FavItemUi,
	onBackClick: () -> Unit,
	viewModel: DetailViewModel = hiltViewModel(),
) {
	val itemData by viewModel.stateData
	LaunchedEffect(key1 = Unit) {
		viewModel.getDetail(item)
	}


	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					itemData.item.title?.let {
						Text(
							text = it, overflow = TextOverflow.Ellipsis, maxLines = 2
						)
					}
				},
				navigationIcon = {
					IconButton(onClick = onBackClick.also {
						//viewModel.saveProgression(itemData.item.id, sliderPosition.toInt())
					}) {
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
							if (!itemData.isFav) {
								viewModel.saveFav(
									itemData.item
								)
							} else {
								viewModel.deleteFav(itemData.item.id)
							}
						},
					) {
						Icon(
							imageVector = when (itemData.isFav) {
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
					contentScale = ContentScale.FillWidth,
					modifier = Modifier.aspectRatio(16f / 9f)
				)
			}
			itemData.item.type.takeIfNotNullOrBlank()?.let {
				Text(text = it, Modifier.padding(10.dp))
			}

			itemData.item.description.takeIfNotNullOrBlank()?.let {
				ExpandableMangaDescription(false, it)
			}

			var sliderPosition by remember(itemData.item.progression) { mutableFloatStateOf(itemData.item.progression.toFloat()) }
			Log.d("TAG", "DetailContent: $sliderPosition && ${itemData.item.progression.toFloat()}")
			//sliderPosition = itemData.item.progression.toFloat()
			if (itemData.item.lastEntry > 0) {
				Column {
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

					Box(modifier = Modifier.fillMaxWidth()) {
						Text(modifier = Modifier.align(Alignment.CenterStart), text = "0")
						Text(modifier = Modifier.align(Alignment.CenterEnd), text = itemData.item.lastEntry.toString())
					}

					Row(
						modifier = Modifier
							.fillMaxWidth(),
						verticalAlignment = Alignment.CenterVertically,
						horizontalArrangement = Arrangement.Center,
					) {

						RepeatingButton(onClick = { sliderPosition-- }) {
							Icon(Icons.Filled.Remove, contentDescription = null)

						}
						Text("${sliderPosition.toInt()}", Modifier.padding(NORMAL_SPACING))
						RepeatingButton(onClick = { sliderPosition++ }) {
							Icon(Icons.Filled.Add, contentDescription = null)
						}
					}
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