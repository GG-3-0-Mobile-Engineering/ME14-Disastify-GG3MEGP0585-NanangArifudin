package com.arfdn.disastify

import android.app.Application
import com.arfdn.disastify.di.DataSourceModule
import com.arfdn.disastify.di.NetworkModule
import com.arfdn.disastify.di.RepositoryModule
import com.arfdn.disastify.di.UseCaseModule
import com.arfdn.disastify.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class DisastifyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Koin modules
        startKoin {
            androidContext(this@DisastifyApplication)
            modules(appModules)
        }
    }

    companion object {
        // List all Koin modules that will be used in the application
        private val appModules = listOf(
            DataSourceModule.dataSourceModule,
            NetworkModule.module,
            RepositoryModule.module,
            UseCaseModule.module,
            ViewModelModule.module
            // Add more modules as needed
        )
    }
}