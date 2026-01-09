package com.since.taskwave.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.since.taskwave.data.mapper.toQuotes
import com.since.taskwave.domain.repo.db.QuotesRepo
import com.since.taskwave.domain.repo.network.NetworkApiRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class OneTimeCoWorker @AssistedInject constructor(
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
                  quotesRepo.insertUpdate(quotes = it.toQuotes("One time request"))
              }
                val gson = Gson().toJson(quotes.success)
                val data = Data.Builder()
                    .putString("key",gson)
                    .build()
                Result.success(data)
            }else{
                Result.failure()
            }

        }catch (e: Exception){
            e.printStackTrace()
            Result.failure()
        }
    }
}