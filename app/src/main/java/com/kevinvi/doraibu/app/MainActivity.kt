package com.kevinvi.doraibu.app

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.kevinvi.doraibu.app.navigation.DoraibuNavHost
import com.kevinvi.doraibu.app.navigation.DoraibuNavigator
import com.kevinvi.doraibu.app.ui.DoraibuApp
import com.kevinvi.doraibu.app.ui.theme.DoraibuAppTheme
import com.kevinvi.ui.NotificationPermissionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

	private val viewModel: MainActivityViewModel by viewModels()

	@OptIn(ExperimentalPermissionsApi::class)
	@RequiresApi(Build.VERSION_CODES.S)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)




		setContent {
			val uiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

			val darkTheme = shouldUseDarkTheme(uiState)

			// Update the dark content of the system bars to match the theme
			DisposableEffect(darkTheme) {
				enableEdgeToEdge()
				onDispose {}
			}
			DoraibuAppTheme(
				useDarkTheme = darkTheme,
			)
			{

				val isPersistEnable by viewModel.isPersistEnable.collectAsStateWithLifecycle(
					initialValue = true,
				)
				DoraibuApp { innerPadding, doraibuNavigator ->


					when {
						Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
							val permissionState =
								rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)

							when {
								permissionState.status.isGranted || isPersistEnable -> {

									DoraibuNavHost(
										navController = doraibuNavigator.navController,
										startScreen = DoraibuNavigator.startScreen,
										innerPadding = innerPadding,
									)
								}

								else -> {
									val context = LocalContext.current
									NotificationPermissionDialog(
										onConfirmation = {
											permissionState.launchPermissionRequest()
											viewModel.enableNotificationPersist(context, true)
										},
									)
								}
							}
						}

						else -> {

							DoraibuNavHost(
								navController = doraibuNavigator.navController,
								startScreen = DoraibuNavigator.startScreen,
								innerPadding = innerPadding,
							)
						}
					}
				}
			}
		}

	}
}

@Composable
private fun shouldUseDarkTheme(
	uiState: Int,
): Boolean = when (uiState) {
	0 -> isSystemInDarkTheme()
	1 -> false
	2 -> true
	else -> false
}