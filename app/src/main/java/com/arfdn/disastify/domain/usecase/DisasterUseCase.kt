package com.arfdn.disastify.domain.usecase

import com.arfdn.disastify.domain.model.Disaster
import com.arfdn.disastify.domain.repository.DisasterRepository

class DisasterUseCase(private val disasterRepository: DisasterRepository) {
    suspend fun getDisasterReports(): Disaster {
        return disasterRepository.getDisasterReports()
    }
}