package com.kevinvi.scan.data.repository

import com.kevinvi.scan.data.model.ScanItem

interface ScanRepository {
	suspend fun getMangaByName(name:String): ScanItem

	suspend fun getLastestChapter(id: String)

	fun getImage(id:String, covertArt: String)
}