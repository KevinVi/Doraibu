package com.kevinvi.anime.data.model

import com.kevinvi.common.extension.empty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeEpisodesItem(
	val pagination: PaginationItem,
	val data: List<EpisodeInfo>,
)

@Serializable
data class PaginationItem(
	@SerialName("last_visible_page") val lastVisiblePage: Int,
	@SerialName("has_next_page") val hastNextPage: Boolean,
)

@Serializable
data class EpisodeInfo(
	@SerialName("mal_id") val lastEpisode: Int,
	val url: String? = String.empty,
	val title: String? = String.empty,
	val aired: String? = String.empty,
)