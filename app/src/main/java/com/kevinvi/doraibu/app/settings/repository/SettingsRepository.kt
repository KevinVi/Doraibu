package com.kevinvi.doraibu.app.settings.repository

import com.kevinvi.doraibu.app.settings.DarkModeConfig

interface SettingsRepository {


	suspend fun setDarkModeConfig(darkModeConfig: Int)

	suspend fun setDynamicColor(useDynamicColor: Boolean)
}