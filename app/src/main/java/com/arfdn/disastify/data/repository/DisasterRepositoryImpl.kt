package com.arfdn.disastify.data.repository

import android.util.Log
import com.arfdn.disastify.data.source.DisasterDataSource
import com.arfdn.disastify.domain.model.Disaster
import com.arfdn.disastify.domain.repository.DisasterRepository

class DisasterRepositoryImpl(private val remoteDataSource: DisasterDataSource) :
    DisasterRepository {
    override suspend fun getDisasterReports(): Disaster {
        // You may perform any additional data mapping or transformations here
        return Disaster(remoteDataSource.getDisasterReports().result)
    }
}