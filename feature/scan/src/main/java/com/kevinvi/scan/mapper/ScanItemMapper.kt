package com.kevinvi.scan.mapper

import com.kevinvi.common.data.MapperList
import com.kevinvi.scan.data.model.ScanItem
import com.kevinvi.scan.ui.ScanItemUi

object ScanItemMapper : MapperList<ScanItem,ScanItemUi>() {
	override fun mapToUi(item: ScanItem)= ScanItemUi(
		id = item.data.
	)

	override fun mapToData(item: ScanItemUi): ScanItem {
		TODO("Not yet implemented")
	}
}