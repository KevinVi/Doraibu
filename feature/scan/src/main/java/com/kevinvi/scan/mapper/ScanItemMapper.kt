package com.kevinvi.scan.mapper

import com.kevinvi.common.data.MapperList
import com.kevinvi.common.extension.empty
import com.kevinvi.scan.data.model.ScanData
import com.kevinvi.scan.data.model.ScanDescription
import com.kevinvi.scan.data.model.ScanItem
import com.kevinvi.scan.data.model.ScanRelationships
import com.kevinvi.scan.ui.ScanItemDataUi
import com.kevinvi.scan.ui.ScanItemUi
import com.kevinvi.ui.model.FavItemUi

object ScanItemMapper : MapperList<ScanItem, ScanItemUi>() {

	fun mapToDetail(item: ScanItem) = FavItemUi(
		lastEntry = item.data.first().attributes?.chapter?.toInt(),
		createdAt = item.data.first().attributes?.createdAt,
		updatedAt = item.data.first().attributes?.updatedAt,
		linked = item.data.first().attributes?.externalUrl,

		)

	override fun mapToUi(item: ScanItem) = ScanItemUi(
		result = item.result,
		items = item.data.map { mapData(it) },
	)

	private fun mapData(data: ScanData) = ScanItemDataUi(
		id = data.id,
		title = getDescription(data.attributes?.title),
		description = getDescription(data.attributes?.description),
		createdAt = data.attributes?.createdAt,
		updatedAt = data.attributes?.updatedAt,
		image = getImage(data.id, getCoverArt(data.relationships))

	)

	private fun getCoverArt(relation: List<ScanRelationships>?): String {
		relation?.forEach {
			if (it.type == "cover_art") {
				return it.attributes?.fileName ?: String.empty
			}
		}
		return String.empty
	}

	private fun getDescription(desc: ScanDescription?) =
		if (!desc?.fr.isNullOrEmpty()) {
			desc?.fr
		} else if (!desc?.en.isNullOrEmpty()) {
			desc?.en
		} else {
			String.empty
		}

	private fun getImage(id: String, coverArt: String) = "https://uploads.mangadex.org/covers/$id/$coverArt"

	override fun mapToData(item: ScanItemUi): ScanItem {
		TODO("Not yet implemented")
	}
}