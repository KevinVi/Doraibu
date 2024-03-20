package com.kevinvi.tome.data.model

import com.kevinvi.common.extension.empty
import kotlinx.serialization.Serializable

@Serializable
data class TomeItem(
	val items: List<DataItem>? = emptyList(),

	)

@Serializable
data class DataItem(
	val id: String = String.empty,
	val selfLing: String = String.empty,
	val volumeInfo: VolumeInfo? = null,
	val searchInfo: SearchInfo? = null,
)

@Serializable
data class VolumeInfo(
	val title: String? = String.empty,
	val authors: List<String>? = emptyList(),
	val publishedDate: String? = String.empty,
	val description: String? = String.empty,
	val imageLinks: ImageLinks? = null,
)

@Serializable
data class ImageLinks(
	val smallThumbnail: String? = String.empty,
	val thumbnail: String? = String.empty,
)

@Serializable
data class SearchInfo(
	val textSnippet: String? = String.empty,
)