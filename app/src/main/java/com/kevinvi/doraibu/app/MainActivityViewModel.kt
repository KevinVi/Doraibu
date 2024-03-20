package com.kevinvi.doraibu.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinvi.scan.data.repository.ScanRepository
import com.kevinvi.scan.mapper.ScanItemMapper
import com.kevinvi.scan.ui.ScanItemDataUi
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
class MainActivityViewModel @Inject constructor(val scanRepository: ScanRepository) : ViewModel() {

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
		}
	}

}

data class ScanListUiState(val list: List<ScanItemDataUi> = emptyList(), val isLoading: Boolean = true) {

}
