package com.kevinvi.ui


import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Icon {
	data class ImageVectorIcon(val imageVector: ImageVector) : Icon()
	data class DrawableResIcon(@DrawableRes val id: Int) : Icon()
}
