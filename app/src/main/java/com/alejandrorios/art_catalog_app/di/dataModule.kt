package com.alejandrorios.art_catalog_app.di

import com.alejandrorios.art_catalog_app.data.network.BASE_URL
import com.alejandrorios.art_catalog_app.data.network.ArtAPIService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

private const val TIME_OUT = 10L

private val json = Json { ignoreUnknownKeys = true }

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val dataModule = module {
    // Client
    single {
        OkHttpClient
            .Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    // Service
    single { createArtApiService(get()) }
}

fun createArtApiService(retrofit: Retrofit): ArtAPIService = retrofit.create(ArtAPIService::class.java)
