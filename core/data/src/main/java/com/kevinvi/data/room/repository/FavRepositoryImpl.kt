package com.kevinvi.data.room.repository

import android.util.Log
import com.kevinvi.data.room.dao.FavDao
import com.kevinvi.data.room.mapper.FavMapper
import com.kevinvi.ui.model.FavItemUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavRepositoryImpl @Inject constructor(
	private val favDao: FavDao,
) : FavRepository {
	override fun getAll(): Flow<List<FavItemUi>> = favDao.getAll().map {

		Log.d("TAG", "getAll: HEHEHEHEHE getDetail")
		FavMapper.mapListToData(it)
	}

	override fun getById(id: String): Flow<FavItemUi?> = favDao.getById(id).map {
		Log.d("TAG", "getById: getDetail WHYYYYY")
		it?.let { FavMapper.mapToData(it) }
	}

	override suspend fun save(item: FavItemUi) {
		favDao.save(FavMapper.mapToLocal(item))
	}

	override suspend fun enableNotification(id: String, isEnable: Boolean) {
		favDao.enableNotification(id, isEnable)
	}

	override suspend fun delete(id: String) {
		favDao.delete(id)
	}

	override suspend fun updateProgression(id: String, progression: Int) {
		favDao.updateProgression(id, progression)
	}

	override suspend fun updateLastEntry(id: String, lastEntry: Int, updateAt: String) {
		favDao.updateLastEntry(id, lastEntry, updateAt)
	}
}