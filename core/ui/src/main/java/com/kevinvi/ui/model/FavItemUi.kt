package com.kevinvi.ui.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.kevinvi.common.UiModel
import com.kevinvi.common.extension.empty
import com.kevinvi.common.navigation.NavigationUtils
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URLDecoder

@Parcelize
@Serializable
data class FavItemUi(
	val id: String = String.empty,
	val type: String = String.empty,
	val title: String? = String.empty,
	val altTitle: String? = String.empty,
	val description: String? = String.empty,
	val author: String? = String.empty,
	val imageUrl: String? = String.empty,
	var language: String? = String.empty,
	var createdAt: String? = String.empty,
	var updatedAt: String? = String.empty,
	var progression: Int = 0,
	var lastEntry: Int = 0,
	var notification: Boolean = true,
	var isFinished: Boolean = false,
	var isFav: Boolean = false,
	var linked: String? = String.empty,
	val listLinkedId: List<Pair<String,String>>? = emptyList(),
	val linkedAnimeRelations: List<FavItemUi>? = emptyList(),
	val linkedAnimeRecommendations: List<FavItemUi>? = emptyList(),
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
			isFav = false,
			linked = String.empty,
			listLinkedId = emptyList()
		)
	}
}

class AssetParamTypeDetail : NavType<FavItemUi>(isNullableAllowed = false) {
	override fun get(bundle: Bundle, key: String): FavItemUi? {
		return bundle.getParcelable(key)
	}

	override fun parseValue(value: String): FavItemUi {
		return Json { ignoreUnknownKeys = true }.decodeFromString<FavItemUi>(URLDecoder.decode(value, NavigationUtils.URL_ENCODING))
	}

	override fun put(bundle: Bundle, key: String, value: FavItemUi) {
		bundle.putParcelable(key, value)
	}
}
