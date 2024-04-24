package com.kevinvi.scan.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kevinvi.common.extension.takeIfNotNullOrBlank

@Composable
fun ScanSearchResult(
	item: ScanItemDataUi,

	onItemClick: (ScanItemDataUi) -> Unit,
) {

	Card(
		onClick = { onItemClick(item) },
		elevation = CardDefaults.cardElevation(),
		modifier = Modifier
			.padding(10.dp)
			.height(225.dp)
			.width(150.dp)
	)
	{


		Column {
			var state by remember {
				mutableStateOf(false)
			}
			Box(modifier = Modifier.fillMaxWidth()) {
				item.image.takeIfNotNullOrBlank()?.let {
					AsyncImage(
						model = it,
						contentDescription = null,
						contentScale = ContentScale.Crop,
						modifier = Modifier.aspectRatio(2f / 3f),
						onSuccess = {
							state = true
						}

						//colorFilter = ColorFilter.tint(Color.DarkGray)
					)
				}

				if (state) {
					item.title.takeIfNotNullOrBlank()?.let {
						Text(
							text = it,
							Modifier
								.fillMaxWidth()
								.align(Alignment.BottomStart)
								.background(
									Brush.linearGradient(
										0.0f to Color.Transparent,
										1.0f to Color.Black.copy(0.6f),
										start = Offset(0.0f, 10.0f),
										end = Offset(0.0f, 100.0f)
									)
								)
								.padding(10.dp),
							overflow = TextOverflow.Ellipsis,
							maxLines = 3,
							color = Color.White,

							)
					}
				}
			}

		}
	}

}

@Preview
@Composable
fun ScanItemComposable() {

	ScanSearchResult(
		ScanItemDataUi(
			id = "10",
			title = "One piece",
			altTitles = "",
			description = "un mec qui a un équipage et qui veut devenir roi des pirates",
			createdAt = "date 1 ",
			updatedAt = "data 2 ",
			image = "https://uploads.mangadex.org/covers/68112dc1-2b80-4f20-beb8-2f2a8716a430/c3f43d5a-83c4-44bd-a117-b247019329b2.jpg",
			lastChapter = null,
			isFinished = false,
			emptyList()
		),
		onItemClick = {}
	)
}