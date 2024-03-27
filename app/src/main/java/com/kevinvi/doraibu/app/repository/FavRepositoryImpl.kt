package com.kevinvi.doraibu.app.repository

import com.kevinvi.data.room.dao.FavDao
import com.kevinvi.doraibu.app.mapper.FavMapper
import com.kevinvi.doraibu.app.model.FavItemUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavRepositoryImpl @Inject constructor(
	private val favDao: FavDao,
) : FavRepository {
	override fun getAll(): Flow<List<FavItemUi>> = favDao.getAll().map {
		FavMapper.mapListToData(it)
	}

	override fun getById(id: String): Flow<FavItemUi?> = favDao.getById(id).map {
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