package com.kevinvi.common.data

import Mapper
import com.kevinvi.common.UiModel

abstract class MapperList<T, U : UiModel> : Mapper<T, U> {
	open fun mapToListUi(items: List<T>): List<U> = items.map {
		mapToUi(it)
	}

	open fun mapToListData(items: List<U>): List<T> = items.map {
		mapToData(it)
	}
}
