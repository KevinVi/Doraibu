package com.kevinvi.scan.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ScanDetailItem(
	val result: String,
	val response: String,
	val data: List<ScanDetailData>,
)

@Serializable
data class ScanDetailData(
	val id: String,
	val type: String?,
	val attributes: ScanDetailAttributes?= null,
	val relationships: List<ScanDetailRelationships>?= null,
)

@Serializable
data class ScanDetailAttributes(
	val title: String?= null,
	val altTitles: String?= null,
	val description: ScanDescription?= null,
	val createdAt: String?= null,
	val updatedAt: String?= null,
	val fileName: String?= null,
	val chapter: String?= null,
	val translatedLanguage: String?= null,
	val externalUrl: String?= null,
)


@Serializable
data class ScanDetailRelationships(
	val id: String,
	val type: String? = null,
	val related: String? = null,
	val attributes: ScanAttributesImage?= null,
)


