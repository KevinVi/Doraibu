package com.kevinvi.doraibu.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.kevinvi.doraibu.app.worker.enqueueDoraibuWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class DoraibuApplication : Configuration.Provider ,Application() {

	@Inject lateinit var workerFactory: HiltWorkerFactory
	override fun onCreate() {
		super.onCreate()

		Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
		startWorker()
	}

	private fun startWorker() {
		CoroutineScope(Dispatchers.IO).launch {

			WorkManager
				.getInstance(this@DoraibuApplication)
				.apply {
					when {
						else -> enqueueDoraibuWorker()
					}
				}
		}
	}

	override val workManagerConfiguration: Configuration
		get() = Configuration.Builder()
			.setWorkerFactory(workerFactory)
			.build()

}