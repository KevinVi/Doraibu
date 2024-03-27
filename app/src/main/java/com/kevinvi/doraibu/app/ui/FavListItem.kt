package com.kevinvi.doraibu.app.ui

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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kevinvi.common.extension.takeIfNotNullOrBlank
import com.kevinvi.doraibu.app.model.FavItemUi

@Composable
fun FavListItem(

	item: FavItemUi,
	onItemClick: (item: FavItemUi) -> Unit,
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
			item.imageUrl.takeIfNotNullOrBlank()?.let {
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