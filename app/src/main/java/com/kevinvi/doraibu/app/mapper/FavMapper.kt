package com.kevinvi.doraibu.app.mapper

import com.kevinvi.data.room.model.FavItem
import com.kevinvi.doraibu.app.model.FavItemUi

object FavMapper {

	fun mapToData(item: FavItem) = FavItemUi(
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
		linked =  item.linked,
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
		linked =  item.linked,
	)

}