package com.kevinvi.anime.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface AnimeRepositoryModule {

	@Binds
	fun bindsScanRepository(animeRepositoryImpl: AnimeRepositoryImpl): AnimeRepository
}