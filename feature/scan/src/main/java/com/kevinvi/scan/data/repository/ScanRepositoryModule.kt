package com.kevinvi.scan.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ScanRepositoryModule {

	@Binds
	fun bindsScanRepository(rssRepositoryImpl: ScanRepositoryImpl): ScanRepository
}