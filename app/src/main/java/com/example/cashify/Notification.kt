package com.example.cashify

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.newFixedThreadPoolContext

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "Loan reminder"
const val messageExtra = "Hey, check your loans, right now"

class Notification : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(titleExtra)
            .setContentText(messageExtra)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(200, notification.build())

    }
}