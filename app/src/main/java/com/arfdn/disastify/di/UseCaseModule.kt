package com.arfdn.disastify.di

import com.arfdn.disastify.data.repository.DisasterRepositoryImpl
import com.arfdn.disastify.domain.repository.DisasterRepository
import com.arfdn.disastify.domain.usecase.DisasterUseCase
import org.koin.dsl.module

object UseCaseModule {
    val module = module {
        single { DisasterUseCase(get()) }
    }
}