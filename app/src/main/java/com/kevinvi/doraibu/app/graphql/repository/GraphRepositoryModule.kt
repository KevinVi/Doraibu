package com.kevinvi.doraibu.app.graphql.repository

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GraphRepositoryModule {

	@Provides
	@Singleton
	fun povideApolloClient(): ApolloClient=
		ApolloClient.Builder()
			.serverUrl("https://anilist.co/graphiql")
			.build()

	@Provides
	@Singleton
	fun provideAnimeClient(apolloClient: ApolloClient):
		GraphRepositoryImpl = GraphRepositoryImpl(apolloClient)
}