package com.kevinvi.anime.data.model

import com.kevinvi.common.extension.empty
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
	val title: String? =  String.empty,
	@SerialName("main_picture") val picture: AnimePicture? = null,
	val synopsis: String? = String.empty,
	@SerialName("num_episodes") val numEpisodes: Int = 0,

)

@Serializable
data class AnimePicture(
	val medium: String? =  String.empty,
	val large: String? =  String.empty,
	)