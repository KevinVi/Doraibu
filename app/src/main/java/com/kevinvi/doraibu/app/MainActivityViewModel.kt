package com.kevinvi.doraibu.app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevinvi.common.extension.launchIO
import com.kevinvi.doraibu.app.store.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel  @Inject constructor(
	@ApplicationContext context: Context,
) : ViewModel() {

	val isPersistEnable = SettingsDataStore.isEnable(context)

	fun enableNotificationPersist(context: Context, value: Boolean) {
		viewModelScope.launchIO {
			SettingsDataStore.write(context, value)
		}
	}

	val settingsUiState = SettingsDataStore.getTheme(context).stateIn(
		scope = viewModelScope,
		started = SharingStarted.Eagerly,
		initialValue = 0)
}