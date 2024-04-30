package com.kevinvi.doraibu.app.graphql.repository

import com.kevinvi.doraibu.app.graphql.model.AnimeModel
import com.kevinvi.doraibu.app.graphql.model.Media

interface GraphRepository {

	suspend fun getAnime(query: String): List<Media>
}