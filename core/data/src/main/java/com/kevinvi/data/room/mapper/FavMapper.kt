package com.kevinvi.data.room.mapper

import com.kevinvi.common.extension.empty
import com.kevinvi.data.room.model.FavItem
import com.kevinvi.ui.model.FavItemUi

object FavMapper {

	fun mapToData(item: FavItem) = FavItemUi(
		id = item.id,
		type = item.type ?: String.empty,
		title = item.title,
		description = item.description,
		author = item.author,
		imageUrl = item.imageUrl,
		language = item.language,
		createdAt = item.createdAt,
		updatedAt = item.updatedAt,
		progression = item.progression ?: 0,
		lastEntry = item.lastEntry ?: 0,
		notification = item.notification,
		isFinished = item.isFinished,
		linked = item.linked,
	)

	fun mapListToData(list: List<FavItem>) = list.map { mapToData(it) }

	fun mapToLocal(item: FavItemUi) = FavItem(
		id = item.id,
		type = item.type,
		title = item.title,
		description = item.description,
		author = item.author,
		imageUrl = item.imageUrl,
		language = item.language,
		createdAt = item.createdAt,
		updatedAt = item.updatedAt,
		progression = item.progression,
		lastEntry = item.lastEntry,
		notification = item.notification,
		isFinished = item.isFinished,
		linked = item.linked,
	)

}