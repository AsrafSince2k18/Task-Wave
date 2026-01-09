package com.since.taskwave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.since.taskwave.presentation.notification.notifi.NotificationHandler
import com.since.taskwave.presentation.notification.notifi.NotificationViewModel
import com.since.taskwave.presentation.notification.utility.notificationPermission
import com.since.taskwave.presentation.screen.HomeScreenRoot
import com.since.taskwave.ui.theme.TaskWaveTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskWaveTheme {

                val viewModel : NotificationViewModel = viewModel()
                if(!notificationPermission()){
                    NotificationHandler(
                        viewModel.state
                    ) {
                        viewModel.onAction(it)
                    }
                }else {
                    HomeScreenRoot()
                }
            }
        }
    }
}


