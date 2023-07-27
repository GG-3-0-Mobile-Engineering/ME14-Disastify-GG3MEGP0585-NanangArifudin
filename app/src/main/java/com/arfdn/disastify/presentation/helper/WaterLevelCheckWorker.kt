package com.arfdn.disastify.presentation.helper

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class WaterLevelCheckWorker(
    appContext: Context,
    workerParams: WorkerParameters,
) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        try {
            // Lakukan pengecekan kondisi, misalnya dengan memanggil fungsi yang berisi pengecekan tingkat air berdasarkan data aktual atau data dummy.
            // Jika kondisi terpenuhi, panggil metode untuk menampilkan notifikasi.
            val waterLevelThreshold = 1 // Ambang batas tingkat air untuk menampilkan notifikasi
            val currentWaterLevel =
                5// Ambil tingkat air dari sumber data (misalnya dari sensor atau data dummy)
//                if (currentWaterLevel >= waterLevelThreshold) {
            // Tampilkan notifikasi
            NotificationHelper.showNotificationWithAction(
                applicationContext,
                "Water Level Alert",
                "Tingkat air saat ini: $currentWaterLevel"
            )
//                } else {
//                    Log.d("doWork", "doWork: elsebranch")
//                }
            return Result.success()
        } catch (e: Exception) {
            Log.e("workcek", "doWork: ${e.message}")
            return Result.failure()
        }
    }
}