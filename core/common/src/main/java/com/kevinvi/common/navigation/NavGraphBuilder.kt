package com.kevinvi.common.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.composableBottomBar(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        //enterTransition = { fadeIn(tweenScreen()) },
        //exitTransition = { fadeOut(tweenScreen()) },
        //popEnterTransition = { fadeIn(tweenScreen()) },
        //popExitTransition = { fadeOut(tweenScreen()) },
        content = content,
    )
}

fun NavGraphBuilder.navigationBottomBar(
    startDestination: String,
    route: String,
    builder: NavGraphBuilder.() -> Unit,
) {
    navigation(
        startDestination = startDestination,
        route = route,
       // enterTransition = { fadeIn(tweenScreen()) },
       // exitTransition = { fadeOut(tweenScreen()) },
        //popEnterTransition = { fadeIn(tweenScreen()) },
        //popExitTransition = { fadeOut(tweenScreen()) },
        builder = builder,
    )
}
