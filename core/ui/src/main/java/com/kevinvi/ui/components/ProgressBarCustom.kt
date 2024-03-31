package com.kevinvi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ProgressBarCustom(score: Int = 0) {

	val gradient = Brush.linearGradient(
		listOf(
			Color(0xFFF95075),
			Color(0xFFBE6BE5)
		)
	)

	val progressFactor by remember(score) {
		mutableStateOf(score * 0.01f)
	}

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(30.dp)
			.padding(4.dp)
			.border(
				width = 2.dp,
				brush = Brush.linearGradient(
					colors = listOf(
						Color.Magenta,
						Color.Magenta,
					)
				),
				shape = RoundedCornerShape(30.dp)
			)
			.clip(
				RoundedCornerShape(
					topStartPercent = 60,
					topEndPercent = 60,
					bottomEndPercent = 60,
					bottomStartPercent = 60
				)
			)
			.background(Color.Transparent),
		verticalAlignment = Alignment.CenterVertically
	) {

		if (score < 20) {
			Text(
				text = (score).toString() + "%",
				modifier = Modifier
					.fillMaxHeight()
					.fillMaxWidth(),
				color = Color.DarkGray,
				textAlign = TextAlign.Center
			)
		} else {
			Button(
				contentPadding = PaddingValues(1.dp),
				onClick = { },
				modifier = Modifier
					.fillMaxWidth(progressFactor)
					.background(brush = gradient),
				enabled = false,
				elevation = null,
				colors = buttonColors(
					containerColor = Color.Transparent,
					disabledContainerColor = Color.Transparent
				)
			) {

				Text(
					text = (score).toString() + "%",
					modifier = Modifier
						.fillMaxHeight()
						.fillMaxWidth(),
					color = Color.White,
					textAlign = TextAlign.Center
				)
			}
		}
	}
}