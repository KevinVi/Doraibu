package com.kevinvi.doraibu.app.graphql.repository

import com.apollographql.apollo3.ApolloClient
import com.kevinvi.doraibu.app.graphql.model.Media

class GraphRepositoryImpl(val apolloClient: ApolloClient) : GraphRepository {

	override fun getAnime(): List<Media> {
		apolloClient.query(ActivityReplyQ)
	}
}