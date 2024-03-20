package com.kevinvi.tome.mapper

import com.kevinvi.tome.data.model.TomeItem
import com.kevinvi.tome.ui.TomeContainer
import com.kevinvi.tome.ui.TomeItemUi
import com.kevinvi.common.data.MapperList
import com.kevinvi.common.extension.empty

object TomeItemMapper : MapperList<TomeItem, TomeContainer>() {
	override fun mapToUi(item: TomeItem) = item.items?.let {
		TomeContainer(

			data = it.map {
				TomeItemUi(
					id = it.id,
					title = it.volumeInfo?.title ?: String.empty,
					authors = it.volumeInfo?.authors ?: emptyList(),
					publishedDate = it.volumeInfo?.publishedDate ?: String.empty,
					description = it.volumeInfo?.description ?: String.empty,
					picture = it.volumeInfo?.imageLinks?.thumbnail ?: String.empty,
					textSnippet = it.searchInfo?.textSnippet ?: String.empty,
				)
			}
		)
	} ?: TomeContainer(emptyList())

	override fun mapToData(item: TomeContainer): TomeItem {
		TODO("Not yet implemented")
	}
}