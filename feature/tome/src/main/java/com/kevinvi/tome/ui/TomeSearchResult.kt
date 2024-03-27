package com.kevinvi.tome.ui

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
import com.kevinvi.common.extension.empty
import com.kevinvi.common.extension.takeIfNotNullOrBlank

@Composable
fun TomeSearchResult(item: TomeItemUi) {

	Card(
		elevation = CardDefaults.cardElevation(),
		modifier = Modifier
			.padding(10.dp)
			.width(100.dp)
	)
	{

		Column {
			item.picture.takeIfNotNullOrBlank()?.let {
				AsyncImage(
					model = it.replace("http","https"),
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
fun TomeItemComposable() {

	TomeSearchResult(
		TomeItemUi(
			id = "10",
			title = "One piece",
			authors = emptyList(),
			publishedDate = String.empty,
			description = "un mec qui a un équipage et qui veut devenir roi des pirates",
			picture = "https://uploads.mangadex.org/covers/68112dc1-2b80-4f20-beb8-2f2a8716a430/c3f43d5a-83c4-44bd-a117-b247019329b2.jpg",
			textSnippet = String.empty,
		)
	)
}