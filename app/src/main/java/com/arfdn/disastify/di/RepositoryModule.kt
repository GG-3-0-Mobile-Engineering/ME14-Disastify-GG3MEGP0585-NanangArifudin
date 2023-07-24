package com.arfdn.disastify.di

import com.arfdn.disastify.data.repository.DisasterRepositoryImpl
import com.arfdn.disastify.domain.repository.DisasterRepository
import org.koin.core.context.GlobalContext.get
import org.koin.dsl.module

object RepositoryModule {
    val module =  module {
        single<DisasterRepository> { DisasterRepositoryImpl(get()) }
    }
}