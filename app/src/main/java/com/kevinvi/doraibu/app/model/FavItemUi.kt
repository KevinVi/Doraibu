package com.kevinvi.doraibu.app.model

data class FavItemUi(
	val id: String,
	val type: String?,
	val title: String?,
	val description: String?,
	val author: String?,
	val imageUrl: String?,
	val language: String?,
	val createdAt: String?,
	val updatedAt: String?,
	val progression: Int?,
	val lastEntry: Int?,
	val notification: Boolean = true,
	val isFinished: Boolean = false,
	val linked: String?,
)