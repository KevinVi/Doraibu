package com.kevinvi.anime.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeItem(
	val data: List<DataItem>? = emptyList(),

	)

@Serializable
data class DataItem(
	val node: NodeItem,
)

@Serializable
data class NodeItem(
	val id: Int? = 0,
	val title: String? = "",
	@SerialName("main_picture") val picture: AnimePicture? = null,
)

@Serializable
data class AnimePicture(
	val medium: String? = "",
	val large: String? = "",
	)