package com.arfdn.disastify.di

import com.arfdn.disastify.presentation.report.DisasterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val module = module {
        viewModel { DisasterListViewModel(get()) }
    }
}