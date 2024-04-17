package com.kevinvi.doraibu.app.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinvi.common.UiModel
import com.kevinvi.doraibu.app.settings.repository.SettingsRepository
import com.kevinvi.doraibu.app.store.FavDataStore
import com.kevinvi.doraibu.app.store.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
	private val repository: @JvmSuppressWildcards SettingsRepository,
	@ApplicationContext context: Context,
) : ViewModel() {


	val settingsUiState = SettingsDataStore.getTheme(context)
	fun updateDarkModeConfig(darkModeConfig: Int) {
		viewModelScope.launch {
			repository.setDarkModeConfig(darkModeConfig)
		}
	}

	fun updateDynamicColor(useDynamicColor: Boolean) {
		viewModelScope.launch {
			repository.setDynamicColor(useDynamicColor)
		}
	}
}

data class UserSettingsUi(
	val darkModeConfig: Int,
	val useDynamicColor: Boolean,
) : UiModel

sealed interface SettingsUiState {
	data object Loading : SettingsUiState
	data class Success(val settings: UserSettingsUi) : SettingsUiState
}