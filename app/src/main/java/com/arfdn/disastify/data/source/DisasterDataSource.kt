package com.arfdn.disastify.data.source

import com.arfdn.disastify.data.model.DisasterResponse

interface DisasterDataSource {
    suspend fun getDisasterReports(timeperiod: String?,admin: String?,disaster: String?): DisasterResponse
    suspend fun getDisasterReportsByPeriod(start: String?,end: String?): DisasterResponse

}