package com.kevinvi.doraibu.app

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import com.kevinvi.scan.ui.ScanItemDataUi
import com.kevinvi.tome.data.repository.TomeRepository
import com.kevinvi.tome.mapper.TomeItemMapper
import com.kevinvi.ui.model.FavItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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

	private var _stateData: MutableState<DetailUiState> = mutableStateOf(
		DetailUiState(
			FavItemUi.EMPTY,
			true,
			false
		),
	)

	val stateData: State<DetailUiState>
		get() = _stateData

	fun getDetail(favItem: FavItemUi) {
		_stateData.value = _stateData.value.copy(loading = true)
		favRepository.getById(favItem.id).map { item ->
			if (item != null) {
				_stateData.value = _stateData.value.copy(item = item, isFav = true)
			}
		}.stateIn(
			scope = viewModelScope,
			started = SharingStarted.Eagerly,
			initialValue = FavListUiState.Loading,
		)
		viewModelScope.launch(Dispatchers.IO) {
			//else
			val id = IdFavoriteUtils().getIdFromFavorite(favItem.id)
			when (favItem.type) {
				TypeUi.SCAN.name -> {
					//call scan

					Log.d("TAG", "getDetail:4 coucou scan ")
					if (!favItem.isFinished) {
						scanRepository.getLastestChapter(id).let {
							val scan = ScanItemMapper.mapToDetail(it)
							favItem.createdAt = scan.createdAt
							favItem.updatedAt = scan.updatedAt
							favItem.lastEntry = scan.lastEntry
							favItem.linked = scan.linked
						}
					}
					_stateData.value = _stateData.value.copy(item = favItem)
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
			_stateData.value = _stateData.value.copy(isFav = true)

		}
	}

	fun deleteFav(id: String) {
		viewModelScope.launchIO {
			favRepository.delete(id)
			_stateData.value = _stateData.value.copy(isFav = false)

		}
	}

	fun saveProgression(id: String, progression: Int) {
		viewModelScope.launchIO {
			favRepository.updateProgression(id, progression)
		}
	}

	fun setupLastAnimeEpisode(favItem: FavItemUi, episodes: AnimeEpisodesItem) {
		val anime = AnimeItemMapper.mapToEpisodeDetail(episodes)
		favItem.lastEntry = anime.lastEntry
		_stateData.value = _stateData.value.copy(item = favItem)

	}

	private var _stateDataRelation = MutableStateFlow(RelationStation())

	val stateDataRelation: StateFlow<RelationStation>
		get() = _stateDataRelation

	fun relations(listLinkedId: List<Pair<String, String>>?) {
		viewModelScope.launch(Dispatchers.IO) {
			var list = emptyList<ScanItemDataUi>()
			listLinkedId?.forEach {
				if (list.size < 5) {
					scanRepository.getMangaById(it.first).let {
						Log.d("TAG", "search: $it")
						val data = ScanItemMapper.mapToUiRelation(it).items
						if (data.contentRating == "safe"){
							list = list + ScanItemMapper.mapToUiRelation(it).items
						}
					}
				}
			}
			_stateDataRelation.update { it.copy(list = list, loading = false) }
		}
	}
}

data class DetailUiState(
	val item: FavItemUi = FavItemUi.EMPTY,
	val loading: Boolean = true,
	val isFav: Boolean = false,
)

data class RelationStation(
	val loading: Boolean = true,
	val list: List<ScanItemDataUi> = emptyList(),
)
