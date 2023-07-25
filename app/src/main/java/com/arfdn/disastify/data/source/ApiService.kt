package com.arfdn.disastify.data.source

import com.arfdn.disastify.data.model.DisasterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("reports")
    suspend fun getDisasterReports(
        @Query("timeperiod") timeperiod: String?="104800",
        @Query("admin") admin: String?,
        @Query("disaster") disaster: String?)
    : DisasterResponse
}