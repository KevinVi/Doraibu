package com.kevinvi.scan.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
			.width(100.dp)
	)
	{

		Column {
			item.image.takeIfNotNullOrBlank()?.let {
				AsyncImage(
					model = it,
					contentDescription = null,
					contentScale = ContentScale.Crop,
					modifier = Modifier.aspectRatio(2f / 3f)
				)
			}

			item.title.takeIfNotNullOrBlank()?.let {
				Text(text = it, Modifier.padding(10.dp))
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
			description = "un mec qui a un équipage et qui veut devenir roi des pirates",
			createdAt = "date 1 ",
			updatedAt = "data 2 ",
			image = "https://uploads.mangadex.org/covers/68112dc1-2b80-4f20-beb8-2f2a8716a430/c3f43d5a-83c4-44bd-a117-b247019329b2.jpg",
			lastChapter = null,
			isFinished = false
		),
		onItemClick = {}
	)
}