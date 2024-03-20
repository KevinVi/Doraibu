package com.kevinvi.anime.data.repository

import com.kevinvi.anime.data.model.AnimeItem

interface AnimeRepository {

	suspend fun getAnimeByName(name: String): AnimeItem
}