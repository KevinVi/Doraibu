package com.kevinvi.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun Loader() {
	val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loader))
	val progress by animateLottieCompositionAsState(composition)
	LottieAnimation(
		composition = composition,
		iterations = LottieConstants.IterateForever,
	)
}