package com.kevinvi.ui.components

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RepeatingButton(
	modifier: Modifier = Modifier,
	onClick: () -> Unit,
	enabled: Boolean = true,
	interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
	maxDelayMillis: Long = 200,
	minDelayMillis: Long = 5,
	delayDecayFactor: Float = .15f,
	content: @Composable () -> Unit
) {

	val currentClickListener by rememberUpdatedState(onClick)
	val pressed = remember { mutableStateOf(false) }

	IconButton(
		modifier = modifier.pointerInteropFilter {
			Log.d("TAG", "RepeatingButton: $it")
			pressed.value = when (it.action) {
				MotionEvent.ACTION_DOWN -> true
				MotionEvent.ACTION_MOVE -> true
				MotionEvent.ACTION_UP -> false

				else -> false
			}

			true
		},
		onClick = {},
		enabled = enabled,
		interactionSource = interactionSource,
		content = content
	)

	LaunchedEffect(pressed.value, enabled) {
		var currentDelayMillis = maxDelayMillis

		while (enabled && pressed.value) {
			onClick()
			delay(currentDelayMillis)
			currentDelayMillis =
				(currentDelayMillis - (currentDelayMillis * delayDecayFactor))
					.toLong().coerceAtLeast(minDelayMillis)
		}
	}
}