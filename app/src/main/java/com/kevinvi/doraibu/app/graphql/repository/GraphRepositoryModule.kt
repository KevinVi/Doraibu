package com.kevinvi.doraibu.app.graphql.repository

import com.apollographql.apollo3.ApolloClient
import com.kevinvi.anime.data.repository.AnimeRepository
import com.kevinvi.anime.data.repository.AnimeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApolloModule {

	@Provides
	@Singleton
	fun povideApolloClient(): ApolloClient =
		ApolloClient.Builder()
			.serverUrl("https://graphql.anilist.co/")
			.build()
}

@Module
@InstallIn(SingletonComponent::class)
interface GraphRepositoryModule {

	@Binds
	fun bindsGraphRepository(graphRepositoryImpl: GraphRepositoryImpl): GraphRepository
}