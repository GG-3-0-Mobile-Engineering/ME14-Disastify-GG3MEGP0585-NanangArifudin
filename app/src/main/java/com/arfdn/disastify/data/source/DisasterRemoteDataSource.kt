package com.arfdn.disastify.data.source

import com.arfdn.disastify.data.model.DisasterResponse

class DisasterRemoteDataSource(private val apiService: ApiService) : DisasterDataSource {
    override suspend fun getDisasterReports(): DisasterResponse {
        return apiService.getDisasterReports()
    }
}