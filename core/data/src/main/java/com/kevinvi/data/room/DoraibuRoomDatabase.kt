package com.kevinvi.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kevinvi.data.room.dao.FavDao
import com.kevinvi.data.room.model.FavItem

@Database(
	entities = [
		FavItem::class,
	],
	version = 1,
	autoMigrations = [],
)
abstract class DoraibuRoomDatabase : RoomDatabase() {
	abstract fun favDao(): FavDao

}
