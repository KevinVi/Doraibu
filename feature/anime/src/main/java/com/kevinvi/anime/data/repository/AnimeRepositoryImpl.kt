package com.kevinvi.anime.data.repository

import android.util.Log
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
	override suspend fun getAnimeByName(name: String): AnimeItem {
		val client = HttpClient(Android)
		val response: HttpResponse = client.get("https://api.myanimelist.net/v2/anime?q=$name"){
			header("X-MAL-CLIENT-ID","663b99098ebea43aaa47f3916dd2091d")
		}

		val responseBody = response.bodyAsText()
		Log.d("TAG", "getAnimeByName: $name")
		Log.d("TAG", "getAnimeByName:url \"https://api.myanimelist.net/v2/anime?q=$name\"")
		return Json { ignoreUnknownKeys = true }.decodeFromString<AnimeItem>(responseBody)
	}
}