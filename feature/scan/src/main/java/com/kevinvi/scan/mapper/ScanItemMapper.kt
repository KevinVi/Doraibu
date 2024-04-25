package com.kevinvi.scan.mapper

import com.kevinvi.common.TypeUi
import com.kevinvi.common.data.MapperList
import com.kevinvi.common.extension.empty
import com.kevinvi.common.extension.isNotNullOrEmpty
import com.kevinvi.common.utils.IdFavoriteUtils
import com.kevinvi.scan.data.model.ScanData
import com.kevinvi.scan.data.model.ScanDescription
import com.kevinvi.scan.data.model.ScanDetailItem
import com.kevinvi.scan.data.model.ScanItem
import com.kevinvi.scan.data.model.ScanItemSingle
import com.kevinvi.scan.data.model.ScanRelationships
import com.kevinvi.scan.ui.ScanItemDataUi
import com.kevinvi.scan.ui.ScanItemUi
import com.kevinvi.scan.ui.ScanItemUiSingle
import com.kevinvi.ui.model.FavItemUi

object ScanItemMapper : MapperList<ScanItem, ScanItemUi>() {

	fun mapToDetail(item: ScanDetailItem) = FavItemUi(
		lastEntry = item.data.firstOrNull()?.attributes?.chapter?.toDouble()?.toInt() ?: 0,
		createdAt = item.data.firstOrNull()?.attributes?.createdAt,
		updatedAt = item.data.firstOrNull()?.attributes?.updatedAt,
		linked = item.data.firstOrNull()?.attributes?.externalUrl,
	)

	override fun mapToUi(item: ScanItem) = ScanItemUi(
		result = item.result,
		items = item.data.map { mapData(it) },
	)

	fun mapToUiRelation(item: ScanItemSingle) = ScanItemUiSingle(
		result = item.result?: String.empty,
		items = mapData(item.data ?: ScanData(id = String.empty)),
	)

	private fun mapData(data: ScanData) = ScanItemDataUi(
		id = data.id,
		title = getDescription(data.attributes?.title),
		altTitles = getDescription(data.attributes?.altTitles),
		description = getDescription(data.attributes?.description),
		createdAt = data.attributes?.createdAt,
		updatedAt = data.attributes?.updatedAt,
		image = getImage(data.id, getCoverArt(data.relationships)),
		lastChapter = data.attributes?.lastChapter ?: String.empty,
		isFinished = data.attributes?.status == "completed",
		listLinkedId = getRelations(data.relationships),
		contentRating = data.attributes?.contentRating
		)

	private fun getDescription(desc: List<ScanDescription>?): String? {
		desc?.forEach {
			return getDescription(it)
		}
		return String.empty
	}

	private fun getRelations(relation: List<ScanRelationships>?): List<Pair<String, String>> {
		val relations = arrayListOf<Pair<String, String>>()
		relation?.forEach {
			if (it.related?.isNotEmpty() == true && it.type == "manga") {
				relations.add(Pair(it.id, it.related))
			}
		}
		return relations
	}

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

	fun mapToDetail(scanItem: ScanItemDataUi) = FavItemUi(
		id = IdFavoriteUtils().buildId(scanItem.id, TypeUi.SCAN.name),
		type = TypeUi.SCAN.name,
		title = scanItem.title,
		altTitle = scanItem.altTitles,
		description = scanItem.description,
		imageUrl = scanItem.image,
		lastEntry = if (scanItem.lastChapter.isNotNullOrEmpty()) scanItem.lastChapter!!.toDouble().toInt() else 0,
		isFinished = scanItem.isFinished,
		listLinkedId = scanItem.listLinkedId
	)
}