package com.kevinvi.common.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.kevinvi.common.R

class NotificationHelper(context: Context) : ContextWrapper(context) {

	private val notificationManager by lazy {
		context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
	}

	@RequiresApi(Build.VERSION_CODES.O)
	fun sendNewFavNotification(
		notification: NotificationContent,
		notificationIntent: PendingIntent,
	) {
		sendNotification(
			channelId = NEW_ITEM_CATEGORY,
			notification = notification,
			notificationIntent
		)
	}

	@RequiresApi(Build.VERSION_CODES.O)
	fun sendNotification(
		channelId: String,
		notification: NotificationContent,
		notificationIntent: PendingIntent,
	) {
		manageNotificationChannel(channelId)

		val notificationBuilder = NotificationCompat.Builder(this, channelId)
			.setSmallIcon(R.drawable.notification_icon)
			.setContentTitle(getString(R.string.core_common_notification_channel_title))
			.setContentText(notification.description)
			.setContentIntent(notificationIntent)

			.apply {
				if (notification.description.length > MIN_DESC_LENGTH_TO_EXPEND) {
					setStyle(
						NotificationCompat.BigTextStyle()
							.bigText(notification.description),
					)
				}
			}

		notificationManager?.notify(notification.id, notificationBuilder.build())
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun manageNotificationChannel(channelId: String) {
		when (channelId) {
			NEW_ITEM_CATEGORY -> {
				createNotificationChannel(
					channelId = NEW_ITEM_CATEGORY,
					name = getString(R.string.core_common_notification_channel_title),
				)
			}

			else -> {
				createNotificationChannel(
					channelId = DEFAULT_CATEGORY,
					name = getString(R.string.app_name),
				)
			}
		}
	}

	@RequiresApi(Build.VERSION_CODES.O)
	private fun createNotificationChannel(
		channelId: String,
		name: String,
		importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
	) {
		notificationManager?.createNotificationChannel(
			NotificationChannel(channelId, name, importance),
		)
	}

	companion object {
		private const val DEFAULT_CATEGORY = "DEFAULT_CATEGORY"
		private const val NEW_ITEM_CATEGORY = "NEW_FAV_CATEGORY"

		private const val MIN_DESC_LENGTH_TO_EXPEND = 64
	}
}
