package com.arfdn.disastify.di

import com.arfdn.disastify.data.repository.DisasterRepositoryImpl
import com.arfdn.disastify.data.source.DisasterDataSource
import com.arfdn.disastify.data.source.DisasterRemoteDataSource
import com.arfdn.disastify.domain.repository.DisasterRepository
import org.koin.dsl.module

object DataSourceModule {
    val dataSourceModule = module {
        single<DisasterDataSource> { DisasterRemoteDataSource(get()) }
    }
}