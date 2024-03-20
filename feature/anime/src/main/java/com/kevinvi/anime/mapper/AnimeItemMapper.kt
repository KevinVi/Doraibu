package com.kevinvi.anime.mapper

import com.kevinvi.anime.data.model.AnimeItem
import com.kevinvi.anime.data.model.DataItem
import com.kevinvi.anime.ui.AnimeContainer
import com.kevinvi.anime.ui.AnimeItemUi
import com.kevinvi.common.data.MapperList

object AnimeItemMapper : MapperList<AnimeItem, AnimeContainer>() {
	override fun mapToUi(item: AnimeItem) = item.data?.let {
		AnimeContainer(
			data = it.map { AnimeItemUi(it.node.id ?: 0, it.node.title ?: "", it.node.picture?.medium ?: "") }
		)
	} ?: AnimeContainer(emptyList())

	override fun mapToData(item: AnimeContainer): AnimeItem {
		TODO("Not yet implemented")
	}
}