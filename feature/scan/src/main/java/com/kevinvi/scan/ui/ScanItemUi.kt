package com.kevinvi.scan.ui

import android.os.Parcelable
import com.kevinvi.common.UiModel
import com.kevinvi.common.extension.empty
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScanItemUi(
	val result: String,
	val items: List<ScanItemDataUi>,
) : UiModel, Parcelable {
	companion object {
		val EMPTY = ScanItemUi(
			result = String.empty,
			items = emptyList()
		)
	}
}

@Parcelize
data class ScanItemDataUi(
	val id: String,
	val title: String?,
	val description: String?,
	val createdAt: String?,
	val updatedAt: String?,
	val image: String?,
) : UiModel, Parcelable {

	companion object {
		val EMPTY = ScanItemDataUi(
			id = String.empty,
			title = null,
			description = null,
			createdAt = null,
			updatedAt = null,
			image = null,
		)
	}
}
