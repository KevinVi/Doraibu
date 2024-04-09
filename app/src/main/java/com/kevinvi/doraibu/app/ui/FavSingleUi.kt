package com.kevinvi.doraibu.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kevinvi.common.extension.takeIfNotNullOrBlank
import com.kevinvi.ui.components.ProgressBarCustom
import com.kevinvi.ui.model.FavItemUi

@Composable
fun FavSingleUi(

	item: FavItemUi,
	onItemClick: (FavItemUi) -> Unit,
) {
	var state by remember {
		mutableStateOf(false)
	}
	Column(
		modifier = Modifier
			.padding(10.dp)
			.height(200.dp)
			.fillMaxWidth(),
	) {

		Card(
			onClick = { onItemClick(item) },
			elevation = CardDefaults.cardElevation(),
			colors = CardDefaults.cardColors(Color.Transparent)
		)
		{

			Row {
				Card(
					colors = CardDefaults.cardColors(Color.Transparent)
				) {
					item.imageUrl.takeIfNotNullOrBlank()?.let {
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
				}
				Column(Modifier.padding(start = 4.dp).fillMaxHeight()) {

					item.title.takeIfNotNullOrBlank()?.let {
						Text(text = it, fontSize = 30.sp, fontWeight = FontWeight.Bold)
					}
					item.description.takeIfNotNullOrBlank()?.let {
						Text(text = it, maxLines = 3, overflow = TextOverflow.Ellipsis)
					}
					item.type.takeIfNotNullOrBlank()?.let {
						Text(text = it)
					}
					Spacer(modifier = Modifier.weight(1f))
					if (item.progression > 0 && item.lastEntry > 0) {
						val progress = item.progression * 100 / item.lastEntry
						ProgressBarCustom(progress)
					} else {
						ProgressBarCustom(0)
					}
				}

			}
		}
	}
}

@Preview
@Composable
fun FavSingleUiComposable() {

	FavSingleUi(
		FavItemUi(
			id = "10",
			title = "One piece",
			description = "un mec qui a un équipage et qui veut devenir roi des pirates",
			createdAt = "date 1 ",
			updatedAt = "data 2 ",
			imageUrl = "https://uploads.mangadex.org/covers/68112dc1-2b80-4f20-beb8-2f2a8716a430/c3f43d5a-83c4-44bd-a117-b247019329b2.jpg",
		),
		{}
	)
}