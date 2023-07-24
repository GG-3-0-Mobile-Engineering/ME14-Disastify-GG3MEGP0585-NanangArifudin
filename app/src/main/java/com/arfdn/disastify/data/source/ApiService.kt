package com.arfdn.disastify.data.source

import com.arfdn.disastify.data.model.DisasterResponse
import retrofit2.http.GET

interface ApiService {

    @GET("reports")
    suspend fun getDisasterReports(): List<DisasterResponse>
}