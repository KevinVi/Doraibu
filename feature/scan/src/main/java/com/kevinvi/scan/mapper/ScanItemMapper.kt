package com.kevinvi.scan.mapper

import com.kevinvi.common.data.MapperList
import com.kevinvi.common.extension.empty
import com.kevinvi.scan.data.model.ScanDescription
import com.kevinvi.scan.data.model.ScanItem
import com.kevinvi.scan.data.model.ScanRelationships
import com.kevinvi.scan.ui.ScanItemUi

object ScanItemMapper : MapperList<ScanItem, ScanItemUi>() {
	override fun mapToUi(item: ScanItem) = ScanItemUi(
		id = item.data[0].id,
		title = item.data[0].attributes?.title?.toString(),
		description = getDescription(item.data[0].attributes?.description),
		createdAt = item.data[0].attributes?.createdAt,
		updatedAt = item.data[0].attributes?.updatedAt,
		image = getImage(item.data[0].id, getCoverArt(item.data[0].relationships))
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