package com.kevinvi.doraibu.app.worker

import android.content.Context
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerInitializer : Initializer<WorkManager> {

    @Provides
    @Singleton
    override fun create(@ApplicationContext context: Context): WorkManager {
        WorkManager.initialize(context, Configuration.Builder().build())
        return WorkManager.getInstance(context)
    }

    // No dependencies on other libraries.
    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
