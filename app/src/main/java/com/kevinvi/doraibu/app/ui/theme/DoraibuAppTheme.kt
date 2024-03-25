package com.kevinvi.doraibu.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun DoraibuAppTheme(
	useDarkTheme: Boolean = isSystemInDarkTheme(),
	useDynamicTheme: Boolean = false,
	content: @Composable () -> Unit,
) {

	MaterialTheme(
		shapes = Shapes,
		content = content,
	)
}

val Shapes = Shapes(
	medium = RoundedCornerShape(16.dp),
	large = RoundedCornerShape(32.dp),
	extraLarge = RoundedCornerShape(64.dp),
)
