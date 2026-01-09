package com.since.taskwave.presentation.notification.notifi

import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.since.taskwave.presentation.notification.state_action.NotificationAction
import com.since.taskwave.presentation.notification.state_action.NotificationState
import com.since.taskwave.presentation.notification.utility.notificationPermission
import com.since.taskwave.presentation.notification.utility.notificationRational
import com.since.taskwave.presentation.notification.utility.openSetting

@Composable
fun NotificationHandler(
    state: NotificationState,
    action: (NotificationAction) -> Unit
) {

    val context = LocalContext.current
    val componentActivity = context as ComponentActivity

    val launchPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { perm->
        action(NotificationAction.NotificationInfo(
            rational = componentActivity.notificationRational(),
            permission = perm
        ))
    }


    LaunchedEffect(true) {
        if(!context.notificationPermission()){
            launchPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }


    if(state.rational){
        NotificationDialog(
            rational = componentActivity.notificationRational(),
            onLaunchPermission = {
                launchPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            },
            onSettingOpen = {}
        )
    }

    if(state.dialog){
        NotificationDialog(
            rational = componentActivity.notificationRational(),
            onLaunchPermission = {},
            onSettingOpen = {
                context.openSetting()
            }
        )
    }


    val lifeCycle = LocalLifecycleOwner.current


    DisposableEffect(lifeCycle.lifecycle) {
        val observe = LifecycleEventObserver{_,event->
            if (event== Lifecycle.Event.ON_START){
                if (!context.notificationPermission() && state.dialog){
                    action(NotificationAction.NotificationInfo(
                        rational = componentActivity.notificationRational(),
                        permission = context.notificationPermission()
                    ))
                }

                if(context.notificationPermission()){
                    action(NotificationAction.NotificationInfo(
                        rational = componentActivity.notificationRational(),
                        permission = context.notificationPermission()
                    ))
                    action(NotificationAction.DismissDialog)
                }
            }
        }
       lifeCycle.lifecycle.addObserver(observer = observe)
        onDispose {
            lifeCycle.lifecycle.removeObserver(observe)
        }

    }


}


@Composable
fun NotificationDialog(
    rational: Boolean,
    onLaunchPermission : () -> Unit,
    onSettingOpen : () -> Unit
) {

    AlertDialog(
        onDismissRequest = {},
        confirmButton = {

            Column(
                modifier = Modifier
                    .padding(horizontal = 6.dp, vertical = 4.dp)
                    .fillMaxWidth()
            ) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Button(
                    onClick = {
                        if(rational) onLaunchPermission() else onSettingOpen()
                    },
                    modifier =Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .padding(horizontal = 6.dp)
                ) {
                    Text(text = if(rational) "Launch permission" else "Open setting")
                }
            }

        },
        title = {
            Text(text = "Enable Notifications",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        },
        text = {
            val value = if(rational){
                "Notifications are used to inform you when background network tasks complete or need attention."
            }else{
                "Notifications are disabled. Enable them in settings to receive updates from background tasks."
            }
            Text(text = value,
                style = TextStyle(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }, shape = RoundedCornerShape(22.dp),
        tonalElevation = 4.dp

    )

}