package com.kevinvi.doraibu.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinvi.anime.data.model.AnimeEpisodesItem
import com.kevinvi.anime.data.repository.AnimeRepository
import com.kevinvi.anime.mapper.AnimeItemMapper
import com.kevinvi.common.TypeUi
import com.kevinvi.common.extension.launchIO
import com.kevinvi.common.utils.IdFavoriteUtils
import com.kevinvi.data.room.repository.FavRepository
import com.kevinvi.scan.data.repository.ScanRepository
import com.kevinvi.scan.mapper.ScanItemMapper
import com.kevinvi.tome.data.repository.TomeRepository
import com.kevinvi.ui.model.FavItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
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
			Log.d("TAG", "getDetail: coucou ")
			favRepository.getById(favItem.id).mapNotNull { item ->

				Log.d("TAG", "getDetail: coucou 1 $item ")
				_stateData.update {
					it.copy(item = item ?: FavItemUi.EMPTY)
				}
			}
			//else
			val id = IdFavoriteUtils().getIdFromFavorite(favItem.id)
			when (favItem.type) {
				TypeUi.SCAN.name -> {
					//call scan

					Log.d("TAG", "getDetail: coucou scan ")
					scanRepository.getLastestChapter(id).let {
						val scan = ScanItemMapper.mapToDetail(it)
						favItem.createdAt = scan.createdAt
						favItem.updatedAt = scan.updatedAt
						favItem.lastEntry = scan.lastEntry
						favItem.linked = scan.linked
						_stateData.update {
							Log.d("TAG", "getDetail: coucou scan 1 $favItem")
							it.copy(item = favItem)
						}
					}
				}

				TypeUi.ANIME.name -> {
					//call anime
					Log.d("TAG", "getDetail: coucou scan ")
					animeRepository.getAnimeEpisodes(id).let {
						if (it.pagination.hastNextPage) {
							setupLastAnimeEpisode(favItem, animeRepository.getAnimeEpisodesLast(id, it.pagination.lastVisiblePage.toString()))
						} else {
							setupLastAnimeEpisode(favItem, it)
						}
					}
				}

				TypeUi.TOME.name -> {
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

	fun saveProgression(id: String,progression: Int) {
		viewModelScope.launchIO {
			favRepository.updateProgression(id, progression)
		}
	}

	fun setupLastAnimeEpisode(favItem: FavItemUi, episodes: AnimeEpisodesItem) {
		val anime = AnimeItemMapper.mapToEpisodeDetail(episodes)
		favItem.lastEntry = anime.lastEntry
		_stateData.update {
			Log.d("TAG", "getDetail: coucou scan 1 $favItem")
			it.copy(item = favItem)
		}
	}
}

data class DetailUiState(
	val item: FavItemUi = FavItemUi.EMPTY,
	val loading: Boolean = true,
) {

}