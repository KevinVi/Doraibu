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

	fun getGridWidth(context: Context) = context.favDataStore.data.map { preferences ->
		Log.d("TAG", "isGrid:init ${preferences[displayGridWidth]}")
		preferences[displayGridWidth] ?: 2
	}


	fun getTypeElement(context: Context) = context.favDataStore.data.map { preferences ->
		Log.d("TAG", "isGrid:init ${preferences[displayElement]}")
		preferences[displayElement] ?: 0
	}
	fun saveListPosition(context: Context, grid: Boolean) {
		CoroutineScope(Dispatchers.IO).launch {
			Log.d("TAG", "saveListPosition: $grid")
			context.favDataStore.edit { fav ->
				fav[displayGrid] = grid
			}
		}
	}

	fun saveGridWith(context: Context, width: Int) {
		CoroutineScope(Dispatchers.IO).launch {
			Log.d("TAG", "saveGridWith: $width")
			context.favDataStore.edit { fav ->
				fav[displayGridWidth] = width
			}
		}
	}

	fun saveElementDisplay(context: Context, type: Int) {
		CoroutineScope(Dispatchers.IO).launch {
			Log.d("TAG", "saveElementDisplay: $type")
			context.favDataStore.edit { fav ->
				fav[displayElement] = type
			}
		}
	}


	const val DEFAULT_VALUE = false
	private val displayGrid = booleanPreferencesKey("display_grid")
	private val displayGridWidth = intPreferencesKey("display_grid_with")
	private val displayElement = intPreferencesKey("display_element")
}