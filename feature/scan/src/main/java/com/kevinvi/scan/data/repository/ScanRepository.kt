package com.kevinvi.scan.data.repository

interface ScanRepository {
	fun getMangaByName(name:String)

	fun getLastestChapter(id: String)

	fun getImage(id:String, covertArt: String)
}