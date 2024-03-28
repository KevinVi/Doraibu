package com.kevinvi.data.room.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavRepositoryModule {

	@Binds
	fun bindsFavRepository(favRepositoryImpl: FavRepositoryImpl): FavRepository
}
