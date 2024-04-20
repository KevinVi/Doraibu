package com.kevinvi.common.notifications

import android.graphics.Bitmap
import kotlin.random.Random

data class NotificationContent(
    val id: Int = Random.nextInt(),
    val description: String,
)
