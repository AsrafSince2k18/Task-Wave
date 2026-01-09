package com.since.taskwave.data.worker

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.since.taskwave.MyApp.Companion.CHANNEL_ID
import com.since.taskwave.R
import com.since.taskwave.data.modal.network.QuoteNetwork
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationCoWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters
): CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            val getKey = workerParameters.inputData.getString("key")
                ?: throw IllegalArgumentException("Invalidate key")

            val quotes = Gson().fromJson(getKey, QuoteNetwork::class.java)

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notification = NotificationCompat.Builder(context,CHANNEL_ID)
                .setContentTitle(quotes.author)
                .setContentText(quotes.quote)
                .setSmallIcon(R.drawable.logo)
                .build()

            notificationManager.notify(quotes.id,notification)

            Result.success()
        }catch (e: Exception){
            e.printStackTrace()
            Result.failure()
        }
    }
}