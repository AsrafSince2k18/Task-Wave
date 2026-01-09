package com.since.taskwave.data.worker

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.since.taskwave.MyApp.Companion.CHANNEL_ID
import com.since.taskwave.R
import com.since.taskwave.data.mapper.toQuotes
import com.since.taskwave.data.repo_impl.WorkerRepoImpl.Companion.CANCEL_WORKER_KEY
import com.since.taskwave.domain.repo.db.QuotesRepo
import com.since.taskwave.domain.repo.network.NetworkApiRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PeriodicTimeCoWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val networkApiRepo: NetworkApiRepo,
    private val quotesRepo: QuotesRepo
): CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result {
        return try {
            val quotes = networkApiRepo.getQuotes()
            if(quotes is com.since.taskwave.domain.error.Result.Success){
              quotes.success?.let {
                  quotesRepo.insertUpdate(quotes = it.toQuotes("Periodic time request"))
                  val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                  val intent = Intent(context, CancelReceiver::class.java).apply {
                      action = "CANCEL_WORKER"
                      putExtra("key",CANCEL_WORKER_KEY)
                  }

                  val pendingIntent = PendingIntent.getBroadcast(context,
                      1,
                      intent,
                      PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

                  val notification = NotificationCompat.Builder(context,CHANNEL_ID)
                      .setContentTitle(it.author)
                      .setContentText(it.quote)
                      .setSmallIcon(R.drawable.logo)
                      .addAction(R.drawable.logo,"Cancel",pendingIntent)
                      .build()

                  notificationManager.notify(it.id,notification)
              }

                Result.success()
            }else{
                Result.failure()
            }
        }catch (e: Exception){
            e.printStackTrace()
            Result.failure()
        }
    }
}