package com.kevinvi.doraibu.app

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.kevinvi.scan.data.repository.ScanRepositoryImpl
import com.kevinvi.scan.mapper.ScanItemMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : FragmentActivity() {


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		CoroutineScope(Dispatchers.IO).launch {

			val toto = ScanItemMapper.mapToUi(ScanRepositoryImpl().getMangaByName("dandadan"))

			Log.d("TAG", "onCreate: $toto")
		}
	}
}