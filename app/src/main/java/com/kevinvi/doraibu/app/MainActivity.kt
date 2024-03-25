package com.kevinvi.doraibu.app

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.fragment.app.FragmentActivity
import com.kevinvi.doraibu.app.navigation.DoraibuNavHost
import com.kevinvi.doraibu.app.navigation.DoraibuNavigator
import com.kevinvi.doraibu.app.ui.DoraibuApp
import com.kevinvi.doraibu.app.ui.MainScreen
import com.kevinvi.doraibu.app.ui.theme.DoraibuAppTheme
import com.kevinvi.scan.data.repository.ScanRepositoryImpl
import com.kevinvi.scan.mapper.ScanItemMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

	private val viewModel: MainActivityViewModel by viewModels()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MainScreen()

			DoraibuAppTheme {
				DoraibuApp { innerPadding, doraibuNavigator ->
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