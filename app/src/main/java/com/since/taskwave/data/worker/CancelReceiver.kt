package com.since.taskwave.data.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.WorkManager

class CancelReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val key = intent?.getStringExtra("key")
            ?: throw IllegalArgumentException("Receiver invalidate key")

        when(intent.action){
            "CANCEL_WORKER" -> {
                Log.d("t1", "onReceive: ${intent.action}")
                val workManager = WorkManager.getInstance(context)
                workManager.cancelUniqueWork(key)
            }
        }

    }

}