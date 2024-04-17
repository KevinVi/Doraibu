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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.favDataStore: DataStore<Preferences> by preferencesDataStore(name = "private_fav_list")

object FavDataStore {
	fun isGrid(context: Context) = context.favDataStore.data.map { preferences ->
		Log.d("TAG", "isGrid:init ${preferences[displayGrid]}")
		preferences[displayGrid] ?: DEFAULT_VALUE
	}
	fun saveListPosition(context: Context, grid: Boolean) {
		CoroutineScope(Dispatchers.IO).launch {
			Log.d("TAG", "saveListPosition: $grid")
			context.favDataStore.edit { fav ->
				fav[displayGrid] = grid
			}
		}
	}


	const val DEFAULT_VALUE = false
	private val displayGrid = booleanPreferencesKey("display_grid")
}