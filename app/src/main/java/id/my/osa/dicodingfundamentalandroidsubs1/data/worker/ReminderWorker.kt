package id.my.osa.dicodingfundamentalandroidsubs1.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import id.my.osa.dicodingfundamentalandroidsubs1.R
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.retrofit.ApiConfig

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val apiService = ApiConfig.getApiService()
            val response = apiService.getEvents(active = -1, limit = 1)
            val event = response.listEvents.firstOrNull()

            if (event != null) {
                val eventName = event.name ?: "Dicoding Event"
                val eventTime = event.beginTime ?: "Check the app for details"
                showNotification(eventName, eventTime)
            }

            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }

    private fun showNotification(eventName: String, eventTime: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Daily event reminder notifications"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_upcoming_24)
            .setContentTitle(eventName)
            .setContentText(eventTime)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val CHANNEL_ID = "event_reminder_channel"
        private const val CHANNEL_NAME = "Event Reminder"
        private const val NOTIFICATION_ID = 1
    }
}
