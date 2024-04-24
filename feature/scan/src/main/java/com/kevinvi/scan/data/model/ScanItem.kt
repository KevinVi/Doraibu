package com.kevinvi.scan.data.model

import com.kevinvi.common.extension.empty
import kotlinx.serialization.Serializable

@Serializable
data class ScanItem(
	val result: String,
	val response: String,
	val data: List<ScanData>,
)

@Serializable
data class ScanItemSingle(
	val result: String,
	val response: String,
	val data: ScanData,
)

@Serializable
data class ScanData(
	val id: String,
	val attributes: ScanAttributes? = null,
	val relationships: List<ScanRelationships>? = null,
)

@Serializable
data class ScanAttributes(
	val title: ScanDescription? = null,
	val altTitles: List<ScanDescription>? = null,
	val description: ScanDescription? = null,
	val createdAt: String? = null,
	val updatedAt: String? = null,
	val fileName: String? = null,
	val chapter: String? = null,
	val translatedLanguage: String? = null,
	val externalUrl: String? = null,
	val status: String? = null,
	val lastVolume: String? = null,
	val lastChapter: String? = null,
)

@Serializable
data class ScanAttributesImage(
	val fileName: String? = null,
)

@Serializable
data class ScanRelationships(
	val id: String,
	val type: String? = null,
	val related: String? = String.empty,
	val attributes: ScanAttributesImage? = null,
)

@Serializable
data class ScanDescription(
	val en: String? = null,
	val fr: String? = null,
)

