package com.kevinvi.anime.ui

import android.os.Parcelable
import com.kevinvi.common.UiModel
import com.kevinvi.common.extension.empty
import kotlinx.parcelize.Parcelize

data class AnimeContainer(
	val data: List<AnimeItemUi>,
): UiModel

@Parcelize
data class AnimeItemUi(
	val id: Int,
	val title: String,
	val picture: String,
) : UiModel, Parcelable {
	companion object {
		val EMPTY = AnimeItemUi(
			id = 0,
			title = String.empty,
			picture = String.empty
		)
	}
}