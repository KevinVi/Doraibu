package com.kevinvi.anime.ui

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kevinvi.common.extension.takeIfNotNullOrBlank

@Composable
fun AnimeDetailItemUi(

	item: AnimeItemUi,
	onBackClick: () -> Unit,
) {

	AnimeDetailContent(
		item = item,
		onBackClick = onBackClick
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimeDetailContent(
	item: AnimeItemUi,
	onBackClick: () -> Unit,
) {

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(text = "Anime")
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
				)
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

			item.picture.takeIfNotNullOrBlank()?.let {
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

		}

	}
}

@PreviewDynamicColors
@PreviewScreenSizes
@Composable
fun RssDetailsScreenPreview() {
	AnimeDetailContent(
		item = AnimeItemUi(
			id = 10,
			title = "One piece",
			picture = "https://uploads.mangadex.org/covers/68112dc1-2b80-4f20-beb8-2f2a8716a430/c3f43d5a-83c4-44bd-a117-b247019329b2.jpg",
		),
		onBackClick = {},
	)
}