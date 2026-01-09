package com.since.taskwave.data.repo_impl

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.since.taskwave.data.worker.NotificationCoWorker
import com.since.taskwave.data.worker.OneTimeCoWorker
import com.since.taskwave.data.worker.PeriodicTimeCoWorker
import com.since.taskwave.domain.repo.worker.WorkerRepo
import java.time.Duration

class WorkerRepoImpl(
    private val context: Context
) : WorkerRepo {

    private val workerManager by lazy {
        WorkManager.getInstance(context)
    }

    private val constraint = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()


    override suspend fun oneTimeWorker() {

        val oneCoWorker = OneTimeWorkRequestBuilder<OneTimeCoWorker>()
            .setConstraints(constraints = constraint)
            .build()

        val notificationCoWorker = OneTimeWorkRequestBuilder<NotificationCoWorker>()
            .setConstraints(constraints = constraint)
            .build()

        workerManager.beginWith(oneCoWorker)
            .then(notificationCoWorker)
            .enqueue()

    }

    override suspend fun periodicTimeWorker() {

        val periodicTimeCoWorker = PeriodicWorkRequestBuilder<PeriodicTimeCoWorker>(
            repeatInterval = Duration.ofMinutes(15L))
            .setConstraints(constraints = constraint)
            .build()

        workerManager.enqueueUniquePeriodicWork(
            CANCEL_WORKER_KEY,
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicTimeCoWorker)

    }

    companion object{
        const val CANCEL_WORKER_KEY  ="worker_key"
    }
}