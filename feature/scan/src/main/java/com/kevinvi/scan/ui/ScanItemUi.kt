package com.kevinvi.scan.ui

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
@Serializable
@Parcelize
data class ScanItemDataUi(
	val id: String,
	val title: String?,
	val description: String?,
	val createdAt: String?,
	val updatedAt: String?,
	val image: String?,
	val lastChapter: String? = String.empty,
	val isFinished: Boolean ,
) : UiModel, Parcelable {

	companion object {
		val EMPTY = ScanItemDataUi(
			id = String.empty,
			title = null,
			description = null,
			createdAt = null,
			updatedAt = null,
			image = null,
			lastChapter  = "",
			isFinished = false
		)
	}
}

class AssetParamTypeScan : NavType<ScanItemDataUi>(isNullableAllowed = false) {
	override fun get(bundle: Bundle, key: String): ScanItemDataUi? {
		return bundle.getParcelable(key)
	}

	override fun parseValue(value: String): ScanItemDataUi {
		return Json { ignoreUnknownKeys = true }.decodeFromString<ScanItemDataUi>(URLDecoder.decode(value, NavigationUtils.URL_ENCODING))
	}

	override fun put(bundle: Bundle, key: String, value: ScanItemDataUi) {
		bundle.putParcelable(key, value)
	}
}
