package com.arfdn.disastify.domain.usecase

import com.arfdn.disastify.domain.model.Disaster
import com.arfdn.disastify.domain.repository.DisasterRepository

class DisasterUseCase(private val disasterRepository: DisasterRepository) {
    suspend fun getDisasterReports(timeperiod: String?,admin: String?,disaster: String?): Disaster {
        return disasterRepository.getDisasterReports(timeperiod,admin, disaster)
    }

    suspend fun getDisasterReportsByPeriod(start: String?,end: String?): Disaster {
        return disasterRepository.getDisasterReportsByPeriod(start,end)
    }
}