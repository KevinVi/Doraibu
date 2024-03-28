package com.kevinvi.common.utils

import javax.inject.Inject

class IdFavoriteUtils @Inject constructor(){
	public fun buildId(id: String, type: String) = "$id;$type"

	public fun getIdFromFavorite(id: String) = id.split(";").first()
	public fun getTypeFromId(id: String) = id.split(";").last()
}