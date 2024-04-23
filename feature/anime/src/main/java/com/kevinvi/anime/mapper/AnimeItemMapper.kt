package com.kevinvi.anime.mapper

import com.kevinvi.anime.data.model.AnimeEpisodesItem
import com.kevinvi.anime.data.model.AnimeItem
import com.kevinvi.anime.ui.AnimeContainer
import com.kevinvi.anime.ui.AnimeItemUi
import com.kevinvi.common.TypeUi
import com.kevinvi.common.data.MapperList
import com.kevinvi.common.utils.IdFavoriteUtils
import com.kevinvi.ui.model.FavItemUi

object AnimeItemMapper : MapperList<AnimeItem, AnimeContainer>() {
	override fun mapToUi(item: AnimeItem) = item.data?.let {
		AnimeContainer(
			data = it.map { AnimeItemUi(it.node.id ?: 0, it.node.title ?: "", it.node.picture?.large ?: "", it.node.synopsis, it.node.numEpisodes) }
		)
	} ?: AnimeContainer(emptyList())

	override fun mapToData(item: AnimeContainer): AnimeItem {
		TODO("Not yet implemented")
	}

	fun mapToDetail(animeItem: AnimeItemUi) = FavItemUi(
		id = IdFavoriteUtils().buildId(animeItem.id.toString(), TypeUi.SCAN.name),
		type = TypeUi.ANIME.name,
		title = animeItem.title,
		description = animeItem.description,
		lastEntry = animeItem.numEpisodes,
		imageUrl = animeItem.picture,
	)

	fun mapToEpisodeDetail(animeItem: AnimeEpisodesItem) = FavItemUi(
		lastEntry = animeItem.data.lastOrNull()?.lastEpisode ?: 0,
	)

}