package com.arfdn.disastify.domain.repository

import com.arfdn.disastify.data.model.DisasterResponse
import com.arfdn.disastify.domain.model.Disaster

interface DisasterRepository {
    suspend fun getDisasterReports(timeperiod: String?,admin: String?,disaster: String?): Disaster
    suspend fun getDisasterReportsByPeriod(start: String?,end: String?): Disaster
}