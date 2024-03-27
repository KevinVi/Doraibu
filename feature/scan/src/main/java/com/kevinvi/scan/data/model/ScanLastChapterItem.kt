package com.kevinvi.scan.data.model

import android.health.connect.datatypes.units.Volume
import com.kevinvi.common.extension.empty

data class ScanLastChapterItem (
	val data : List<ScanChapter>? = emptyList(),
)

data class ScanChapter(
	val id:String?,
	val type: String?,
	val attributes:ChapterAttributes?,
)

data class ChapterAttributes(
	val volume: String? = String.empty,
	val chapter: String? = String.empty,
	val title: String? = String.empty,
	val externalUrl: String? = String.empty,
	val publishAt: String? = String.empty,
	val readableAt: String? = String.empty,
	val createdAt: String? = String.empty,
	val updatedAt: String? = String.empty,

)

