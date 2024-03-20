package com.kevinvi.doraibu.app

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.fragment.app.FragmentActivity
import com.kevinvi.doraibu.app.ui.MainScreen
import com.kevinvi.scan.data.repository.ScanRepositoryImpl
import com.kevinvi.scan.mapper.ScanItemMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

	private val viewModel : MainActivityViewModel by viewModels()
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MainScreen()
		}



		CoroutineScope(Dispatchers.IO).launch {

			val toto = viewModel.scanRepository.getMangaByName("dandadan")
			viewModel.scanRepository.getMangaByName("dodod")

			//val toto = ScanItemMapper.mapToUi(ScanRepositoryImpl().getMangaByName("tnjnfenzkfj"))

		//	Log.d("TAG", "onCreate: $toto")
		}
	}
}