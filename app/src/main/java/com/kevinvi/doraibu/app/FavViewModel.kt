package com.kevinvi.doraibu.app

import android.content.Context
import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinvi.common.TypeUi
import com.kevinvi.data.room.repository.FavRepository
import com.kevinvi.doraibu.app.store.FavDataStore
import com.kevinvi.ui.model.FavItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
	val favRepository: FavRepository,
	@ApplicationContext context: Context,
) : ViewModel() {

	private var _stateData = MutableStateFlow(FavListUiState.Success())

	val stateData: StateFlow<FavListUiState.Success>
		get() = _stateData

	fun search(text: String) {
		_stateData.update {
			it.copy(list = trueList.filter {
				it.title?.contains(text, ignoreCase = true) == true ||
					it.altTitle?.contains(text, ignoreCase = true
				) == true
			})
		}
	}

	fun filterElement(type: Int){

		when(type){
			0-> {
				_stateData.update {it.copy(list = trueList)}
			}
			1->{
				_stateData.update {it.copy(list = trueList.filter { it.type == TypeUi.SCAN.name })}
			}
			2->{
				_stateData.update {it.copy(list = trueList.filter { it.type == TypeUi.ANIME.name })}
			}
		}
	}

	private var trueList: List<FavItemUi> = emptyList()

	val getDisplay = FavDataStore.isGrid(context)
	val getGridWidth = FavDataStore.getGridWidth(context)
	val getTypeElement = FavDataStore.getTypeElement(context)

	val favUiState: StateFlow<FavListUiState> =
		favRepository.getAll()
			.map {
				trueList = it
				_stateData.update { data -> data.copy(list = it) }
				FavListUiState.Success(
					list = it
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
	data class Success(var list: List<FavItemUi> = emptyList()) : FavListUiState

}
