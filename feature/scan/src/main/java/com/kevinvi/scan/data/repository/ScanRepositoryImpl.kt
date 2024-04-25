package com.kevinvi.scan.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.kevinvi.data.room.KtorClient.client
import com.kevinvi.scan.data.model.ScanDetailItem
import com.kevinvi.scan.data.model.ScanItem
import com.kevinvi.scan.data.model.ScanItemSingle
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ScanRepositoryImpl @Inject constructor() : ScanRepository {
	private val json = Json { ignoreUnknownKeys = true }
	override suspend fun getMangaByName(name: String): ScanItem {

		val response: HttpResponse = client.get("https://api.mangadex.org/manga?title=$name&includes[]=cover_art&contentRating[]=safe")

		val responseBody = response.bodyAsText()
		Log.d("TAG", "getMangaByName: $name")
		return json.decodeFromString<ScanItem>(responseBody)
	}

	override suspend fun getMangaById(id: String): ScanItemSingle {

		val url = "https://api.mangadex.org/manga/$id?includes[]=cover_art&contentRating[]=safe"
		val response: HttpResponse = client.get(url)

		val responseBody = response.bodyAsText()
		Log.d("TAG", "getMangaByName: $id")
		Log.d("TAG", "getMangaById: $url ")
		return json.decodeFromString<ScanItemSingle>(responseBody)
	}

	override suspend fun getLastestChapter(id: String): ScanDetailItem {
		val response: HttpResponse = client.get("https://api.mangadex.org/manga/$id/feed?translatedLanguage[]=fr&order[chapter]=desc&limit=1&includes[]=cover_art")
		val responseBody = response.bodyAsText()
		Log.d("TAG", "getLastestChapter: $responseBody")
		return json.decodeFromString<ScanDetailItem>(responseBody)
	}

	@RequiresApi(Build.VERSION_CODES.O)
	override suspend fun getLastUpdateList(): ScanItem {

		val response: HttpResponse =
			client.get("https://api.mangadex.org/manga?includes[]=cover_art&includes[]=artist&includes[]=author&contentRating[]=safe&hasAvailableChapters=true&availableTranslatedLanguage[]=fr&limit=20")

		val responseBody = response.bodyAsText()
		return json.decodeFromString<ScanItem>(responseBody)
	}

	override fun getImage(id: String, covertArt: String) {
		TODO("Not yet implemented")
	}
}