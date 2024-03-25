package com.kevinvi.doraibu.app.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kevinvi.doraibu.app.navigation.BottomNavigationScreen
import com.kevinvi.doraibu.app.navigation.DoraibuNavigator
import com.kevinvi.doraibu.app.navigation.isTopLevelScreenInHierarchy
import com.kevinvi.doraibu.app.navigation.rememberDoraibuNavigator
import com.kevinvi.doraibu.app.ui.theme.DoraibuAppTheme
import com.kevinvi.ui.Icon

@Composable
fun DoraibuApp(
    content: @Composable (PaddingValues, DoraibuNavigator) -> Unit,
) {
    val jetpackNavigator = rememberDoraibuNavigator()

    val navBackStackEntry by jetpackNavigator.navController.currentBackStackEntryAsState()

    Scaffold(
        content = { content(it, jetpackNavigator) },
        bottomBar = {
            BottomNavigationBar(
                items = DoraibuNavigator.destinations,
                currentDestination = navBackStackEntry?.destination,
            ) { screen ->
                jetpackNavigator.navigateToScreen(screen)
            }
        },
    )
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavigationScreen>,
    currentDestination: NavDestination?,
    onClick: (screen: BottomNavigationScreen) -> Unit,
) {
    NavigationBar {
        items.forEach { screen ->
            val selected = currentDestination.isTopLevelScreenInHierarchy(screen)
            NavigationBarItem(
                icon = {
                    val icon = when {
                        selected -> screen.selectedIcon
                        else -> screen.unselectedIcon
                    }
                    when (icon) {
                        is Icon.ImageVectorIcon -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = stringResource(screen.nameResourceId),
                        )

                        is Icon.DrawableResIcon -> Icon(
                            painter = painterResource(icon.id),
                            contentDescription = stringResource(screen.nameResourceId),
                        )
                    }
                },
                label = { Text(stringResource(screen.nameResourceId)) },
                selected = selected,
                onClick = { onClick(screen) },
            )
        }
    }
}

@PreviewLightDark
@Composable
fun DefaultPreview() {
    DoraibuAppTheme {
        DoraibuApp { _, _ -> }
    }
}
