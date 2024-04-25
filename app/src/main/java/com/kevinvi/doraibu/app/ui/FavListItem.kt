package com.kevinvi.doraibu.app.ui

import android.widget.ProgressBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kevinvi.common.extension.takeIfNotNullOrBlank
import com.kevinvi.ui.components.ProgressBarCustom
import com.kevinvi.ui.model.FavItemUi

@Composable
fun FavListItem(

	item: FavItemUi,
	onItemClick: (FavItemUi) -> Unit,
	displayProgress: Boolean = true,
) {
	var state by remember {
		mutableStateOf(false)
	}
	Column(
		modifier = Modifier
			.padding(10.dp)
			.width(100.dp),
	) {

		Card(
			onClick = { onItemClick(item) },
			elevation = CardDefaults.cardElevation(),
			colors = CardDefaults.cardColors(Color.Transparent)
		)
		{

			Column {
				Box(modifier = Modifier.fillMaxWidth()) {
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
								maxLines = 1,
								color = Color.White,

								)
						}
					}
				}

			}

		}
		if (displayProgress) {
			if (item.progression > 0 && item.lastEntry > 0) {
				val progress = item.progression * 100 / item.lastEntry
				ProgressBarCustom(progress)
			} else {
				ProgressBarCustom(0)
			}
		}
	}
}