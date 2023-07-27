package com.arfdn.disastify.presentation.helper

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.arfdn.disastify.R
import com.arfdn.disastify.data.model.GeometryDummy
import com.arfdn.disastify.presentation.report.MapReportsActivity
import com.google.gson.Gson

object NotificationHelper {
    // Fungsi untuk membuat saluran notifikasi (Notification Channel)
    // Metode untuk mendeteksi tingkat air dari data dummy

    private const val NOTIFICATION_CHANNEL_ID = "water_level_channel"
    private const val NOTIFICATION_CHANNEL_NAME = "Water Level Alert"
    private const val NOTIFICATION_CHANNEL_DESCRIPTION = "Channel for water level alerts"
    private const val NOTIFICATION_ID = 123 // Gunakan ID unik untuk setiap notifikasi

    fun showNotificationWithAction(context: Context, title: String, content: String) {
        // Intent untuk tindakan yang akan dijalankan ketika notifikasi di-tap
        val intent = Intent(context, MapReportsActivity::class.java) // Ganti YourActivity dengan aktivitas tujuan Anda
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Konfigurasi notifikasi menggunakan NotificationCompat.Builder
        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Ganti dengan ikon notifikasi yang sesuai
            .setContentTitle(title)
            .setContentText(content)
            .setAutoCancel(true) // Otomatis menghapus notifikasi saat di-tap
            .setContentIntent(pendingIntent) // Tindakan ketika notifikasi di-tap

        // Dapatkan NotificationManagerCompat untuk menampilkan notifikasi
        val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(context)

        // Tampilkan notifikasi
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }


    fun createNotificationChannel(context: Context) {
        // Hanya perlu membuat saluran notifikasi di Android 8.0 (Oreo) atau versi yang lebih baru
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "water_level_channel"
            val channelName = "Water Level Alert"
            val channelDescription = "Channel for water level alerts"

            // Prioritas notifikasi, PRIORITY_HIGH akan menampilkan notifikasi pop-up
            val channelImportance = NotificationManager.IMPORTANCE_HIGH

            val notificationChannel = NotificationChannel(channelId, channelName, channelImportance).apply {
                description = channelDescription
                // Set pengaturan lainnya untuk saluran notifikasi jika diperlukan
            }

            // Dapatkan NotificationManager dari sistem
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Buat saluran notifikasi dengan menggunakan notificationChannel yang sudah diatur sebelumnya
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun checkWaterLevel(context: Context, waterLevelThreshold: Int) {
        // Ambil data dummy dari assets (sebagai contoh, data dummy kita disimpan dalam file JSON di assets dengan nama "dummy_data.json")
        val jsonString = context.assets.open("dummy_data.json").bufferedReader().use { it.readText() }

        // Gunakan library Gson untuk mengonversi jsonString menjadi objek model sesuai dengan struktur data dummy yang Anda buat
        val gson = Gson()
        val geometry = gson.fromJson(jsonString, GeometryDummy::class.java)

        val waterLevel = 5
        if (waterLevel >= waterLevelThreshold) {
            // Jika tingkat air melebihi atau sama dengan ambang batas, tampilkan notifikasi peringatan
            showNotification(context, "Water Level Alert", "Tingkat air saat ini: $waterLevel")
        }

        // Lakukan pengecekan tingkat air (contoh: membandingkan nilai state dengan batas ambang tertentu)
//        if (geometry.geometries.isNotEmpty()) {
////            val waterLevel = geometry.geometries[0].properties.state
//
//        }
    }

    // Metode untuk menampilkan notifikasi dengan konten tertentu
    @RequiresApi(Build.VERSION_CODES.O)
    fun showNotification(context: Context, title: String, content: String) {
        val channelId = "water_level_channel" // Sesuaikan dengan channelId yang telah Anda buat
        val notificationId = 123 // Gunakan ID unik untuk setiap notifikasi

        // Konfigurasi notifikasi
        val notificationBuilder = Notification.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Icon kecil untuk notifikasi (gantilah dengan ikon Anda)
            .setContentTitle(title) // Judul notifikasi ("Water Level Alert")
            .setContentText(content) // Isi notifikasi ("Tingkat air saat ini: 5")
            .setAutoCancel(true) // Otomatis menghapus notifikasi saat di-tap

        // Dapatkan NotificationManager dari sistem
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Tampilkan notifikasi
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    // Metode lain dalam kelas ini sesuai kebutuhan Anda
}
