package com.arfdn.disastify.domain.repository

import com.arfdn.disastify.domain.model.Disaster

interface DisasterRepository {
    suspend fun getDisasterReports(timeperiod: String?,admin: String?,disaster: String?): Disaster
}