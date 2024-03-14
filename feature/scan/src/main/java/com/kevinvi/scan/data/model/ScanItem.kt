package com.kevinvi.scan.data.model

data class ScanItem(
	val result: String,
	val reponse: String,
	val data: List<ScanData>,
)

data class ScanData(
	val id: String,
	val attributes: ScanAttributes?,
)

data class ScanAttributes(
	val title: ScanDescription?,
	val description: ScanDescription?,
	val createdAt: String?,
	val updatedAt: String?,
	val fileName: String?,
	val relationships: List<ScanRelationships>?,
)

data class ScanRelationships(
	val id: String,
	val type: String,
	val attributes: ScanAttributes?,
)

data class ScanDescription(
	val en: String?,
	val fr: String?,
)

