package com.kevinvi.doraibu.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinvi.data.room.mapper.FavMapper
import com.kevinvi.ui.model.FavItemUi
import com.kevinvi.data.room.repository.FavRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
	val favRepository: FavRepository
) :ViewModel(){


	val favUiState: StateFlow<FavListUiState> =
		favRepository.getAll()
			.map {
				FavListUiState.Success(
					list = it,
				)
			}
			.stateIn(
				scope = viewModelScope,
				started = SharingStarted.Eagerly,
				initialValue = FavListUiState.Loading,
			)
}

sealed interface FavListUiState {
	data object Loading : FavListUiState
	data class Success(val list: List<FavItemUi>) : FavListUiState
}
