package com.kevinvi.doraibu.app.graphql.repository

import com.apollographql.apollo3.ApolloClient
import com.kevinvi.doraibu.AnimeQuery
import com.kevinvi.doraibu.app.graphql.mapper.toPageList
import com.kevinvi.doraibu.app.graphql.model.Media
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

class GraphRepositoryImpl @Inject constructor(val apolloClient: ApolloClient) : GraphRepository {
	override suspend fun getAnime(query:String): List<Media> =
		apolloClient.query(AnimeQuery(search = query))
			.execute().data?.Page?.toPageList() ?: emptyList()

}