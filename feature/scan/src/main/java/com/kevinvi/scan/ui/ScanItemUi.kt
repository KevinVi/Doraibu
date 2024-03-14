package com.kevinvi.scan.ui

import android.os.Parcelable
import com.kevinvi.common.UiModel
import com.kevinvi.common.extension.empty
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScanItemUi(
	val id: String,
	val title: String?,
	val description: String?,
	val createdAt: String?,
	val updatedAt: String?,
	val image: String?,
) : UiModel, Parcelable {

	companion object {
		val EMPTY = ScanItemUi(
			id = String.empty,
			title = null,
			description = null,
			createdAt = null,
			updatedAt = null,
			image = null,
		)
	}
}
