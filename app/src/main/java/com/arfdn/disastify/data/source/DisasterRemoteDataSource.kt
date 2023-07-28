package com.arfdn.disastify.data.source

import com.arfdn.disastify.data.model.DisasterResponse

class DisasterRemoteDataSource(private val apiService: ApiService) : DisasterDataSource {
    override suspend fun getDisasterReports(timeperiod: String?,admin: String?,disaster: String?): DisasterResponse {
        return apiService.getDisasterReports(timeperiod,admin,disaster)
    }

    override suspend fun getDisasterReportsByPeriod(start: String?, end: String?): DisasterResponse {
        return apiService.getDisasterReportsByPeriod(start,end)
    }
}