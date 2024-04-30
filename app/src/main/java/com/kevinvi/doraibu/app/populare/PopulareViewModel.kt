package com.kevinvi.doraibu.app.populare

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinvi.common.extension.launchIO
import com.kevinvi.data.room.repository.FavRepository
import com.kevinvi.doraibu.app.graphql.repository.GraphRepository
import com.kevinvi.scan.data.repository.ScanRepository
import com.kevinvi.scan.mapper.ScanItemMapper
import com.kevinvi.scan.ui.ScanItemDataUi
import com.kevinvi.ui.model.FavItemUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopulareViewModel @Inject constructor(
	val scanRepository: ScanRepository,
) : ViewModel() {

	private var _stateData = MutableStateFlow(ScanListUiState())

	val stateData: StateFlow<ScanListUiState>
		get() = _stateData

	fun populare() {
		viewModelScope.launch(Dispatchers.IO) {
			scanRepository.getLastUpdateList().let {

				Log.d("TAG", "search: $it")
				val result = ScanItemMapper.mapToUi(it).items
				_stateData.update { it.copy(list = result, isScanLoading = false) }
			}

		}
	}




}

data class ScanListUiState(
	val list: List<ScanItemDataUi> = emptyList(),
	val isScanLoading: Boolean = true,
) {

}
