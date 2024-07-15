package com.ywhs.climate

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            notify(it)
        }
    }

    private fun notify(context: Context) {
        val builder = NotificationCompat.Builder(context, "MY_channel")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("알림 제목")
            .setContentText("알림 내용")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel_id = "MY_channel"
            val channel_name = "채널이름"
            val descriptionText = "설명글"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channel_id, channel_name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            notificationManager.notify(1002, builder.build())
        }
    }
}