package com.kevinvi.doraibu.app.store

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "private_settings")

object SettingsDataStore {
	fun getTheme(context: Context) = context.settingsDataStore.data.map { preferences ->
		Log.d("TAG", "getTheme:init ${preferences[themeSettings]}")
		preferences[themeSettings] ?: DEFAULT_VALUE
	}

	fun saveTheme(context: Context, theme: Int) {
		CoroutineScope(Dispatchers.IO).launch {
			Log.d("TAG", " getTheme saveListPosition: $theme")
			context.settingsDataStore.edit { fav ->
				fav[themeSettings] = theme
			}
		}
	}

	fun isEnable(context: Context) = context.settingsDataStore.data.map { preferences ->
		preferences[enableNotification] ?: DEFAULT_VALUE_NOTIFICATION
	}

	suspend fun write(context: Context, enable: Boolean) {
		context.settingsDataStore.edit { settings ->
			settings[enableNotification] = enable
		}
	}

	private const val DEFAULT_VALUE_NOTIFICATION = false
	private val enableNotification = booleanPreferencesKey("enable_notification")

	private const val DEFAULT_VALUE = 0
	private val themeSettings = intPreferencesKey("theme")
}