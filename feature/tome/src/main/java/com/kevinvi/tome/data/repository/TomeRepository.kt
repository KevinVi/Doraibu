package com.kevinvi.tome.data.repository

import com.kevinvi.tome.data.model.TomeItem

interface TomeRepository {

	suspend fun getTomeByName(name: String): TomeItem
}