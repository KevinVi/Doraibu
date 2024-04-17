package com.kevinvi.doraibu.app.settings

import androidx.annotation.StringRes
import com.kevinvi.doraibu.R

enum class DarkModeConfig(@StringRes val value: Int) {
	SYSTEM_MODE(R.string.settings_dark_mode_system),
	DARK_MODE(R.string.settings_dark_mode_dark),
	LIGHT_MODE(R.string.settings_dark_mode_light),
}
