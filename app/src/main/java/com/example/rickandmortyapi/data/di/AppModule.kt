package com.example.rickandmortyapi.data.di

import com.example.rickandmortyapi.data.api.ApiService
import com.example.rickandmortyapi.data.repository.Repository
import com.example.rickandmortyapi.ui.fragment.character.CharacterViewModel
import com.example.rickandmortyapi.ui.fragment.detail.CharacterDetailViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.create


const val BASE_URL = "https://rickandmortyapi.com/api/"

val appModule: Module = module {
    single {
        OkHttpClient.Builder()
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }

    single {
        Repository(get())
    }

    viewModel<CharacterViewModel> {
        CharacterViewModel(get())
    }

    viewModel<CharacterDetailViewModel> {
        CharacterDetailViewModel(get())
    }
}
