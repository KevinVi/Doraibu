package com.kevinvi.tome.data.repository

import android.util.Log
import com.kevinvi.tome.data.model.TomeItem
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TomeRepositoryImpl @Inject constructor() : TomeRepository {
	override suspend fun getTomeByName(name: String): TomeItem {
		val client = HttpClient(Android)
		val response: HttpResponse = client.get("https://www.googleapis.com/books/v1/volumes?q=intitle:$name&orderBy=newest&printType=books")

		val responseBody = response.bodyAsText()
		return Json { ignoreUnknownKeys = true }.decodeFromString<TomeItem>(responseBody)
	}
}