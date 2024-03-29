package com.kevinvi.anime.data.repository

import android.util.Log
import com.kevinvi.anime.data.model.AnimeEpisodesItem
import com.kevinvi.anime.data.model.AnimeItem
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor() : AnimeRepository {
	private val client = HttpClient(Android)

	private val json = Json { ignoreUnknownKeys = true }
	override suspend fun getAnimeByName(name: String): AnimeItem {

		val response: HttpResponse = client.get(
			"https://api.myanimelist.net/v2/anime?q=$name&fields=id,title,main_picture,start_date,end_date,synopsis," +
				"created_at,updated_at,status,num_episodes,start_season,broadcast,source,pictures"
		) {
			header("X-MAL-CLIENT-ID", "663b99098ebea43aaa47f3916dd2091d")
		}

		val responseBody = response.bodyAsText()
		Log.d("TAG", "getAnimeByName: $name")
		Log.d("TAG", "getAnimeByName:url \"https://api.myanimelist.net/v2/anime?q=$name\"")
		return json.decodeFromString<AnimeItem>(responseBody)
	}

	override suspend fun getAnimeEpisodes(id: String): AnimeEpisodesItem {
		val response: HttpResponse = client.get("https://api.jikan.moe/v4/anime/$id/episodes") {
		}
		val responseBody = response.bodyAsText()
		return json.decodeFromString<AnimeEpisodesItem>(responseBody)
	}

	override suspend fun getAnimeEpisodesLast(id: String, page: String): AnimeEpisodesItem {
		val response: HttpResponse = client.get("https://api.jikan.moe/v4/anime/$id/episodes?page=$page") {
		}
		val responseBody = response.bodyAsText()
		return json.decodeFromString<AnimeEpisodesItem>(responseBody)
	}
}