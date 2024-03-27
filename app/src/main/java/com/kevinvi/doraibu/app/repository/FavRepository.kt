package com.kevinvi.doraibu.app.repository

import com.kevinvi.data.room.model.FavItem
import com.kevinvi.doraibu.app.model.FavItemUi
import kotlinx.coroutines.flow.Flow

interface FavRepository {

	fun getAll(): Flow<List<FavItemUi>>
	fun getById(id: String): Flow<FavItemUi?>
	suspend fun save(item: FavItemUi)
	suspend fun enableNotification(id: String, isEnable: Boolean)
	suspend fun delete(id: String)
	suspend fun updateProgression(id: String, progression: Int)
	suspend fun updateLastEntry(id: String, lastEntry: Int, updateAt: String)

}