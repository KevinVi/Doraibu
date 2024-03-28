package com.kevinvi.ui.model

import android.os.Parcelable
import com.kevinvi.common.UiModel
import com.kevinvi.common.extension.empty
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavItemUi(
	val id: String = String.empty,
	val type: String = String.empty,
	val title: String? = String.empty,
	val description: String? = String.empty,
	val author: String? = String.empty,
	val imageUrl: String? = String.empty,
	var language: String? = String.empty,
	var createdAt: String? = String.empty,
	var updatedAt: String? = String.empty,
	var progression: Int? = 0,
	var lastEntry: Int? = 0,
	var notification: Boolean = true,
	var isFinished: Boolean = false,
	var linked: String? = String.empty,
) : UiModel, Parcelable {
	companion object {
		val EMPTY = FavItemUi(
			id = String.empty,
			type = String.empty,
			title = String.empty,
			description = String.empty,
			author = String.empty,
			imageUrl = String.empty,
			language = String.empty,
			createdAt = String.empty,
			updatedAt = String.empty,
			progression = 0,
			lastEntry = 0,
			notification = true,
			isFinished = false,
			linked = String.empty,
		)
	}
}