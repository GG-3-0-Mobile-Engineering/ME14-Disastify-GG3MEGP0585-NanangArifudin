package com.arfdn.disastify.di

import com.arfdn.disastify.data.source.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object NetworkModule {
//    https://data.petabencana.id/
    private const val BASE_URL = "https://data.petabencana.id/" // Ganti dengan URL API yang sesuai

    val module = module {
        single { provideHttpLoggingInterceptor() }
        single { provideOkHttpClient(get()) }
        single { provideRetrofit(get(), get()) }
        single { provideApiService(get()) }
        single { provideCoroutineDispatcher() }
//        single<DisasterDataSource> { DisasterRemoteDataSource(get(), get()) }
//        single { DisasterRepository(get()) }
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    private fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient, coroutineDispatcher: CoroutineDispatcher): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .callFactory(okHttpClient)
            .callbackExecutor(coroutineDispatcher.asExecutor()) // Using the provided CoroutineDispatcher as the Executor
            .build()
    }

    private fun CoroutineDispatcher.asExecutor() = Executors.newFixedThreadPool(THREAD_COUNT)

    private const val THREAD_COUNT = 3

    private fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    private fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}