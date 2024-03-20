package com.kevinvi.tome.ui

import android.os.Parcelable
import com.kevinvi.common.UiModel
import com.kevinvi.common.extension.empty
import kotlinx.parcelize.Parcelize

data class TomeContainer(
	val data: List<TomeItemUi>,
) : UiModel

@Parcelize
data class TomeItemUi(
	val id: String,
	val title: String,
	val authors: List<String>,
	val publishedDate: String,
	val description: String,
	val picture: String,
	val textSnippet: String,
) : UiModel, Parcelable {
	companion object {
		val EMPTY = TomeItemUi(
			id = String.empty,
			title = String.empty,
			authors = emptyList(),
			publishedDate = String.empty,
			description = String.empty,
			picture = String.empty,
			textSnippet = String.empty,
		)
	}
}