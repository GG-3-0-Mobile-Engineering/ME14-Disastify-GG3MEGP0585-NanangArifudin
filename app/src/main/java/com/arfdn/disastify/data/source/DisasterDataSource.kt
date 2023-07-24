package com.arfdn.disastify.data.source

import com.arfdn.disastify.data.model.DisasterResponse

interface DisasterDataSource {
    suspend fun getDisasterReports(): List<DisasterResponse>
}