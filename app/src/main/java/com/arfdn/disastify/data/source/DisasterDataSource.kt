package com.arfdn.disastify.data.source

import com.arfdn.disastify.data.model.DisasterResponse

interface DisasterDataSource {
    suspend fun getDisasterReports(timeperiod: String?,admin: String?,disaster: String?): DisasterResponse
}