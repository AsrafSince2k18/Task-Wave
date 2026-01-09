package com.since.taskwave.presentation.notification.utility

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity

fun ComponentActivity.notificationRational(): Boolean{
    return shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)
}

private fun Context.checkPermission(permission:String): Boolean{
    return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.notificationPermission(): Boolean{
    return checkPermission(Manifest.permission.POST_NOTIFICATIONS)
}

fun Context.openSetting():Intent{
    return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package",packageName,null)).also(::startActivity)
}