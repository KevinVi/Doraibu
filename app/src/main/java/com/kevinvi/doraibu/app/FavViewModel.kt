package com.kevinvi.doraibu.app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinvi.common.extension.launchIO
import com.kevinvi.data.room.repository.FavRepository
import com.kevinvi.doraibu.app.store.FavDataStore
import com.kevinvi.ui.model.FavItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
	val favRepository: FavRepository,
	@ApplicationContext context: Context,
) : ViewModel() {

	val getDisplay = FavDataStore.isGrid(context)

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
	data class Success(var list: List<FavItemUi>) : FavListUiState

}
