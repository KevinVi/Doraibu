package com.kevinvi.anime.ui

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

data class AnimeContainer(
	val data: List<AnimeItemUi>,
) : UiModel

@Parcelize
@Serializable
data class AnimeItemUi(
	val id: Int,
	val title: String,
	val picture: String,
	val description: String? = String.empty,
	val numEpisodes: Int = 0,
) : UiModel, Parcelable {
	companion object {
		val EMPTY = AnimeItemUi(
			id = 0,
			title = String.empty,
			picture = String.empty,
			numEpisodes = 0,
			description = String.empty,
		)
	}
}

class AssetParamType : NavType<AnimeItemUi>(isNullableAllowed = false) {
	override fun get(bundle: Bundle, key: String): AnimeItemUi? {
		return bundle.getParcelable(key)
	}

	override fun parseValue(value: String): AnimeItemUi {

		return Json { ignoreUnknownKeys = true }.decodeFromString<AnimeItemUi>(URLDecoder.decode(value, NavigationUtils.URL_ENCODING))
	}

	override fun put(bundle: Bundle, key: String, value: AnimeItemUi) {
		bundle.putParcelable(key, value)
	}
}