package com.kevinvi.doraibu.app.ui

import android.text.Html
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.kevinvi.common.extension.takeIfNotNullOrBlank
import com.kevinvi.doraibu.app.DetailViewModel
import com.kevinvi.doraibu.app.navigation.navigateToDetails
import com.kevinvi.scan.mapper.ScanItemMapper
import com.kevinvi.scan.ui.ScanSearchResult
import com.kevinvi.ui.Dimens.NORMAL_SPACING
import com.kevinvi.ui.Loader
import com.kevinvi.ui.components.ExpandableMangaDescription
import com.kevinvi.ui.components.RepeatingButton
import com.kevinvi.ui.model.FavItemUi

@Composable
fun DetailItemUi(
	item: FavItemUi,
	onBackClick: () -> Unit,
	navController: NavHostController,
) {
	DetailContent(
		item = item,
		onBackClick = onBackClick,
		navController

	)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun DetailContent(
	item: FavItemUi,
	onBackClick: () -> Unit,
	navController: NavHostController,
	viewModel: DetailViewModel = hiltViewModel(),
) {
	val itemData by viewModel.stateData
	LaunchedEffect(key1 = Unit) {
		viewModel.getDetail(item)
	}
	Box(
		modifier = Modifier
			.fillMaxSize()
	) {

		Column(
			modifier = Modifier
				.fillMaxSize()
				.verticalScroll(rememberScrollState())
		) {

			itemData.item.imageUrl.takeIfNotNullOrBlank()?.let {

				AsyncImage(
					model = it,
					contentDescription = null,
					contentScale = ContentScale.Crop,
					modifier = Modifier.aspectRatio(2f / 3f)
				)
			}

			itemData.item.type.takeIfNotNullOrBlank()?.let {
				Text(text = it, Modifier.padding(10.dp))
			}

			itemData.item.description.takeIfNotNullOrBlank()?.let {
				ExpandableMangaDescription(false, Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT).toString())
			}

			var sliderPosition by remember(itemData.item.progression) { mutableFloatStateOf(itemData.item.progression.toFloat()) }
			Log.d("TAG", "DetailContent: $sliderPosition && ${itemData.item.progression.toFloat()}")
			//sliderPosition = itemData.item.progression.toFloat()
			if (itemData.item.lastEntry > 0) {
				Column {
					Slider(
						modifier = Modifier.padding(start = 16.dp, end = 16.dp),
						value = sliderPosition,
						onValueChange = {
							sliderPosition = it
							if (!itemData.isFav) {
								viewModel.saveFav(
									itemData.item
								)
							}
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

					Box(
						modifier = Modifier
							.fillMaxWidth()
							.padding(start = 16.dp, end = 16.dp)
					) {
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

					if (!item.linkedAnimeRecommendations.isNullOrEmpty()) {
						Text("Recommendations")
						LazyRow(
							modifier = Modifier
								.fillMaxWidth(),
							contentPadding = PaddingValues(8.dp),
						) {
							items(item.linkedAnimeRecommendations!!) { it ->
								// Search result
								FavListItem(
									it,
									onItemClick = {
										Log.d("TAG", "MainScreen: data $it")
										navController.navigateToDetails(it)
									},
									false
								)
							}
						}
					}
					if (!item.linkedAnimeRelations.isNullOrEmpty()) {
						Text("Relations")
						LazyRow(
							modifier = Modifier
								.fillMaxWidth(),
							contentPadding = PaddingValues(8.dp),
						) {
							items(item.linkedAnimeRecommendations!!) { it ->
								// Search result
								FavListItem(
									it,
									onItemClick = {
										Log.d("TAG", "MainScreen: data $it")
										navController.navigateToDetails(it)
									},
									false
								)
							}
						}
					}
					if (item.listLinkedId?.isNotEmpty() == true) {
						val related by viewModel.stateDataRelation.collectAsStateWithLifecycle()
						viewModel.relations(item.listLinkedId)
						Log.d("TAG", "DetailContent: RELATED ${related.list}")
						if (related.loading) {
							Loader(true)
						} else {
							Loader(false)
							LazyRow(
								modifier = Modifier
									.fillMaxWidth(),
								contentPadding = PaddingValues(8.dp),
							) {
								items(related.list) { it ->
									// Search result
									ScanSearchResult(
										it,
										onItemClick = {
											Log.d("TAG", "MainScreen: data $it")
											navController.navigateToDetails(ScanItemMapper.mapToDetail(it))
										}
									)
								}
							}
						}
					}
				}
			}

		}
		TopAppBar(
			modifier = Modifier.background(
				Brush.linearGradient(
					0.0f to Color.Black.copy(0.6f),
					1.0f to Color.Transparent,
					start = Offset(0.0f, 210.0f),
					end = Offset(0.0f, 300.0f)
				)
			),
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
				containerColor = Color.Transparent,
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

	}
}