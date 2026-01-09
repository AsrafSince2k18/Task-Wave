package com.since.taskwave.presentation.notification.notifi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.since.taskwave.presentation.notification.state_action.NotificationAction
import com.since.taskwave.presentation.notification.state_action.NotificationState

class NotificationViewModel : ViewModel() {

    var state by mutableStateOf(NotificationState())
        private set


    fun onAction(action: NotificationAction){
        when(action){
            NotificationAction.DismissDialog -> {
                if(state.permission){
                    state=state.copy(
                        dialog = false
                    )
                }
            }
            is NotificationAction.NotificationInfo -> {
                state=state.copy(
                    permission = action.permission,
                    rational = action.rational,
                    dialog = !action.rational && !action.permission
                )
            }
        }
    }

}