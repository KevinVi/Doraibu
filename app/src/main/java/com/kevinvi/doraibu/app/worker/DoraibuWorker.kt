package com.kevinvi.doraibu.app.worker

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kevinvi.common.notifications.NotificationContent
import com.kevinvi.common.notifications.NotificationHelper
import com.kevinvi.doraibu.app.workerRepository.FavWorkerRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DoraibuWorker @AssistedInject constructor(
	@Assisted private val context: Context,
	@Assisted workerParams: WorkerParameters,
	private val repository: FavWorkerRepository,
) : CoroutineWorker(context, workerParams) {

	@RequiresApi(Build.VERSION_CODES.O)
	override suspend fun doWork() = try {


		val notificationHelper = NotificationHelper(context)

		val result = repository.fetchFavUpdate()


		Log.d("TAG", "doWork: HEHEHEHEH $result")
		result.second.let { pair ->



			val desc = pair.joinToString("\n") {
				it.first + " à " + it.second + " nouveaux chapitres"
			}
			notificationHelper.sendNewFavNotification(
				NotificationContent(
					description = desc,
				),
			)

		}
		when {
			result.first -> Result.success()
			else -> Result.failure()
		}
	} catch (ex: Exception) {
		Result.failure()
	}

}
