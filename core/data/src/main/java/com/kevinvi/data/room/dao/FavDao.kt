package com.kevinvi.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kevinvi.data.room.model.FavItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {

	@Query("SELECT * FROM FavItem")
	fun getAll(): Flow<List<FavItem>>

	@Query("SELECT * FROM FavItem WHERE id = :id")
	fun getById(id: String): Flow<FavItem?>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun save(item: FavItem)

	@Query("UPDATE FavItem SET notification = :isEnable WHERE id = :id")
	suspend fun enableNotification(id: String, isEnable: Boolean)

	@Query("DELETE FROM FavItem WHERE id = :id")
	suspend fun delete(id: String)

	@Query("UPDATE FavItem SET progression = :progression WHERE id = :id")
	suspend fun updateProgression(id: String, progression: Int)

	@Query("UPDATE FavItem SET last_entry = :lastEntry AND update_at = :updateAt WHERE id = :id")
	suspend fun updateLastEntry(id: String, lastEntry: Int, updateAt: String)
}