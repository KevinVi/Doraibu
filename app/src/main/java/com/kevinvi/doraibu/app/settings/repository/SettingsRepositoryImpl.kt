package com.kevinvi.doraibu.app.settings.repository

import android.content.Context
import com.kevinvi.doraibu.app.store.FavDataStore
import com.kevinvi.doraibu.app.store.SettingsDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
	@ApplicationContext private val context: Context,
) : SettingsRepository {
	override suspend fun setDarkModeConfig(darkModeConfig: Int) {
		SettingsDataStore.saveTheme(context, darkModeConfig)
	}

	override suspend fun setDynamicColor(useDynamicColor: Boolean) {
	}

}