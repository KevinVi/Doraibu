package com.kevinvi.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.kevinvi.common.extension.empty
import com.kevinvi.ui.R
import kotlin.math.roundToInt

@Composable
fun ExpandableMangaDescription(
	defaultExpandState: Boolean,
	description: String?,
	modifier: Modifier = Modifier,
) {
	Column(modifier = modifier) {
		val (expanded, onExpanded) = rememberSaveable {
			mutableStateOf(defaultExpandState)
		}
		val desc =
			description.takeIf { !it.isNullOrBlank() } ?:  String.empty
		val trimmedDescription = remember(desc) {
			desc
				.replace(whitespaceLineRegex, "\n")
				.trimEnd()
		}
		MangaSummary(
			expandedDescription = desc,
			shrunkDescription = trimmedDescription,
			expanded = expanded,
			modifier = Modifier
				.padding(top = 8.dp)
				.padding(horizontal = 16.dp)
				.clickable { onExpanded(!expanded) }
		)
	}
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
private fun MangaSummary(
	expandedDescription: String,
	shrunkDescription: String,
	expanded: Boolean,
	modifier: Modifier = Modifier,
) {
	val animProgress by animateFloatAsState(
		targetValue = if (expanded) 1f else 0f,
		label = "summary",
	)
	Layout(
		modifier = modifier.clipToBounds(),
		contents = listOf(
			{
				Text(
					text = "\n\n", // Shows at least 3 lines
					style = MaterialTheme.typography.bodyMedium,
				)
			},
			{
				Text(
					text = expandedDescription,
					style = MaterialTheme.typography.bodyMedium,
				)
			},
			{
				SelectionContainer {
					Text(
						text = if (expanded) expandedDescription else shrunkDescription,
						maxLines = Int.MAX_VALUE,
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onBackground,
					)
				}
			},
			{
				val colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background)
				Box(
					modifier = Modifier.background(Brush.verticalGradient(colors = colors)),
					contentAlignment = Alignment.Center,
				) {
					val image = AnimatedImageVector.animatedVectorResource(R.drawable.anim_caret_down)
					Icon(
						painter = rememberAnimatedVectorPainter(image, !expanded),
						contentDescription = "arrow",
						tint = MaterialTheme.colorScheme.onBackground,
						modifier = Modifier.background(Brush.radialGradient(colors = colors.asReversed())),
					)
				}
			},
		),
	) { (shrunk, expanded, actual, scrim), constraints ->
		val shrunkHeight = shrunk.single()
			.measure(constraints)
			.height
		val expandedHeight = expanded.single()
			.measure(constraints)
			.height
		val heightDelta = expandedHeight - shrunkHeight
		val scrimHeight = 24.dp.roundToPx()

		val actualPlaceable = actual.single()
			.measure(constraints)
		val scrimPlaceable = scrim.single()
			.measure(Constraints.fixed(width = constraints.maxWidth, height = scrimHeight))

		val currentHeight = shrunkHeight + ((heightDelta + scrimHeight) * animProgress).roundToInt()
		layout(constraints.maxWidth, currentHeight) {
			actualPlaceable.place(0, 0)

			val scrimY = currentHeight - scrimHeight
			scrimPlaceable.place(0, scrimY)
		}
	}
}
private val whitespaceLineRegex = Regex("[\\r\\n]{2,}", setOf(RegexOption.MULTILINE))

