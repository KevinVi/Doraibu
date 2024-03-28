package com.kevinvi.doraibu.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinvi.anime.data.repository.AnimeRepository
import com.kevinvi.common.TypeUi
import com.kevinvi.common.extension.launchIO
import com.kevinvi.common.utils.IdFavoriteUtils
import com.kevinvi.data.room.repository.FavRepository
import com.kevinvi.scan.data.repository.ScanRepository
import com.kevinvi.scan.mapper.ScanItemMapper
import com.kevinvi.tome.data.repository.TomeRepository
import com.kevinvi.ui.model.FavItemUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
	val favRepository: FavRepository,
	val scanRepository: ScanRepository,
	val animeRepository: AnimeRepository,
	val tomeRepository: TomeRepository,
) : ViewModel() {

	private var _stateData = MutableStateFlow(DetailUiState())

	val stateData: StateFlow<DetailUiState>
		get() = _stateData

	fun getDetail(favItem: FavItemUi) {
		_stateData.update { it.copy(loading = true) }
		viewModelScope.launch(Dispatchers.IO) {
			favRepository.getById(favItem.id).mapNotNull { item ->
				_stateData.update {
					it.copy(item = item ?: FavItemUi.EMPTY)
				}
			}
			//else
			when(favItem.type){
				TypeUi.SCAN.name -> {
					//call scan
					scanRepository.getLastestChapter(favItem.id).let {
						val scan = ScanItemMapper.mapToDetail(it)
						favItem.createdAt = scan.createdAt
						favItem.updatedAt = scan.updatedAt
						favItem.lastEntry = scan.lastEntry
						favItem.linked = scan.linked
						_stateData.update {
							it.copy(item = favItem)
						}
					}
				}
				TypeUi.ANIME.name -> {
					//call anime
				}
				TypeUi.TOME.name ->{
					//call tome
				}
			}

		}
	}

	fun saveFav(item: FavItemUi) {
		viewModelScope.launchIO {
			favRepository.save(item)
		}
	}

	fun deleteFav(id: String) {
		viewModelScope.launchIO {
			favRepository.delete(id)
		}
	}

}

data class DetailUiState(
	val item: FavItemUi = FavItemUi.EMPTY,
	val loading: Boolean = true,
) {

}