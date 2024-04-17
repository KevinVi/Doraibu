package com.kevinvi.doraibu.app.settings

data class UserSettingsLocal(
    val useDynamicColor: Boolean,
    val darkModeConfig: DarkModeConfigLocal,
) {
    enum class DarkModeConfigLocal {
        DARK_MODE_CONFIG_FOLLOW_SYSTEM,
        DARK_MODE_CONFIG_LIGHT,
        DARK_MODE_CONFIG_DARK,
    }
}
