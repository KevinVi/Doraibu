package com.kevinvi.data.room.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity
data class FavItem(
	@PrimaryKey val id: String,
	val type: String?,
	val title: String?,
	val description: String?,
	val author: String?,
	@ColumnInfo(name = "image_url") val imageUrl: String?,
	val language: String?,
	@ColumnInfo(name = "created_at") val createdAt: String?,
	@ColumnInfo(name = "update_at") val updatedAt: String?,
	val progression: Int?,
	@ColumnInfo(name = "last_entry") val lastEntry: Int?,
	@ColumnInfo(name = "notification", defaultValue = "1") val notification: Boolean = true,
	@ColumnInfo(name = "is_finished") val isFinished: Boolean = false,
	val linked: String?
)