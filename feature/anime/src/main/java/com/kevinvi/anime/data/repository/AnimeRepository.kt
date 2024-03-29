package com.kevinvi.anime.data.repository

import com.kevinvi.anime.data.model.AnimeEpisodesItem
import com.kevinvi.anime.data.model.AnimeItem

interface AnimeRepository {

	suspend fun getAnimeByName(name: String): AnimeItem
	suspend fun getAnimeEpisodes(id: String): AnimeEpisodesItem
	suspend fun getAnimeEpisodesLast(id: String, page: String): AnimeEpisodesItem
}