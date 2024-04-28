package com.kevinvi.doraibu.app.graphql.repository

import com.apollographql.apollo3.ApolloClient
import com.kevinvi.doraibu.AnimeQuery
import com.kevinvi.doraibu.app.graphql.mapper.toPageList
import com.kevinvi.doraibu.app.graphql.model.Media

class GraphRepositoryImpl(val apolloClient: ApolloClient) : GraphRepository {

	override suspend fun getAnime(): List<Media> =
		apolloClient.query(AnimeQuery())
			.execute().data?.Page?.toPageList() ?: emptyList()

}