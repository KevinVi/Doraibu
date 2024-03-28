package com.kevinvi.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun Empty(
	isLoading: Boolean,
) {
	if (isLoading) {
		val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty))
		val progress by animateLottieCompositionAsState(composition)
		LottieAnimation(
			modifier = Modifier
				.fillMaxWidth().height(100.dp),
			composition = composition,
		)
	}
}