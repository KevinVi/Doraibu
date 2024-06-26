package com.kevinvi.doraibu.app.workerRepository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FavWorkerRepositoryModule {

	@Binds
	fun bindsFavWorkerRepository(favWorkerRepositoryImpl: FavWorkerRepositoryImpl): FavWorkerRepository
}
