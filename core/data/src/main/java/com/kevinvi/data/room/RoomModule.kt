package com.kevinvi.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

	@Singleton
	@Provides
	fun providesRoomDatabase(@ApplicationContext context: Context) =
		Room.databaseBuilder(
			context = context,
			klass = DoraibuRoomDatabase::class.java,
			name = "jetpack_database",
		).build()

	@Singleton
	@Provides
	fun providesFavDao(doraibuRoomDatabase: DoraibuRoomDatabase) = doraibuRoomDatabase.favDao()

}
