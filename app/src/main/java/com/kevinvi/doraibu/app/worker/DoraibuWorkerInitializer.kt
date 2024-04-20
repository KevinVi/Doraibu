package com.kevinvi.doraibu.app.worker

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType.CONNECTED
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

private const val NAME = "DoraibuWorker"
private const val DEFAULT_INTERVAL = 12L
private val DEFAULT_INTERVAL_TIME_UNIT = TimeUnit.HOURS

fun WorkManager.enqueueDoraibuWorker(
    repeatInterval: Long = DEFAULT_INTERVAL,
    repeatIntervalTimeUnit: TimeUnit = DEFAULT_INTERVAL_TIME_UNIT,
) = enqueueUniquePeriodicWork(
    /* uniqueWorkName = */
    NAME,
    /* existingPeriodicWorkPolicy = */
    ExistingPeriodicWorkPolicy.UPDATE,
    /* periodicWork = */
    PeriodicWorkRequestBuilder<DoraibuWorker>(repeatInterval, repeatIntervalTimeUnit)
        .setConstraints(
            Constraints(
                requiredNetworkType = CONNECTED,
                requiresBatteryNotLow = true,
            ),
        )
        .build(),
)
