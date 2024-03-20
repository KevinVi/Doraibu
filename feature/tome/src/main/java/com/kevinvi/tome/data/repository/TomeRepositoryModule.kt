package com.kevinvi.tome.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface TomeRepositoryModule {

	@Binds
	fun bindsScanRepository(tomeRepositoryImpl: TomeRepositoryImpl): TomeRepository
}