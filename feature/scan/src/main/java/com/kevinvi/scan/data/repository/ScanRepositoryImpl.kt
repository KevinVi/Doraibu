package com.kevinvi.scan.data.repository

import com.kevinvi.scan.data.model.ScanItem
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ScanRepositoryImpl @Inject constructor() : ScanRepository{
	override suspend fun getMangaByName(name: String): ScanItem {
		val client = HttpClient(Android)
		val response: HttpResponse = client.get("https://api.mangadex.org/manga?title=$name&includes[]=cover_art")

		val responseBody = response.bodyAsText()
		return Json{ ignoreUnknownKeys = true }.decodeFromString<ScanItem>(responseBody)

	}

	override suspend fun getLastestChapter(id: String) {
		TODO("Not yet implemented")
	}

	override fun getImage(id: String, covertArt: String) {
		TODO("Not yet implemented")
	}
}