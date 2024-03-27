package com.kevinvi.doraibu.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinvi.anime.data.repository.AnimeRepository
import com.kevinvi.anime.mapper.AnimeItemMapper
import com.kevinvi.anime.ui.AnimeItemUi
import com.kevinvi.common.extension.launchIO
import com.kevinvi.doraibu.app.mapper.FavMapper
import com.kevinvi.doraibu.app.model.FavItemUi
import com.kevinvi.doraibu.app.repository.FavRepository
import com.kevinvi.scan.data.repository.ScanRepository
import com.kevinvi.scan.mapper.ScanItemMapper
import com.kevinvi.scan.ui.ScanItemDataUi
import com.kevinvi.tome.data.repository.TomeRepository
import com.kevinvi.tome.mapper.TomeItemMapper
import com.kevinvi.tome.ui.TomeItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
	val scanRepository: ScanRepository,
	val animeRepository: AnimeRepository,
	val tomeRepository: TomeRepository,
	val favRepository: FavRepository
	) : ViewModel() {

	private var _stateData = MutableStateFlow(ScanListUiState())

	val stateData : StateFlow<ScanListUiState>
		get() = _stateData

	fun search(data: String) {
		_stateData.update { it.copy(isLoading = true) }
		viewModelScope.launch(Dispatchers.IO) {
			scanRepository.getMangaByName(data).let {

				Log.d("TAG", "search: $it")
				val result = ScanItemMapper.mapToUi(it).items
				_stateData.update { it.copy(list = result) }
			}
			_stateData.update { it.copy(isLoading = false) }

			animeRepository.getAnimeByName(data).let {

				Log.d("TAG", "search: $it")
				val result = AnimeItemMapper.mapToUi(it).data
				_stateData.update { it.copy(listAnime = result) }
			}
			_stateData.update { it.copy(isLoading = false) }

			tomeRepository.getTomeByName(data).let {

				Log.d("TAG", "search: $it")
				val result = TomeItemMapper.mapToUi(it).data
				_stateData.update { it.copy(listTome = result) }
			}
			_stateData.update { it.copy(isLoading = false) }
		}
	}
	fun saveFav(item: FavItemUi) {
		viewModelScope.launchIO {
			favRepository.save(item)
		}
	}


}

data class ScanListUiState(
	val list: List<ScanItemDataUi> = emptyList(),
	val listAnime: List<AnimeItemUi> = emptyList(),
	val listTome: List<TomeItemUi> = emptyList(),
	val isLoading: Boolean = true) {

}
