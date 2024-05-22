package com.example.rawggamelistapp.di

import com.example.rawggamelistapp.data.GameRepository
import com.example.rawggamelistapp.data.remote.RawgApiService
import com.example.rawggamelistapp.ui.list.GameListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // Retrofit
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // RawgApiService
    single<RawgApiService> { get<Retrofit>().create(RawgApiService::class.java) }

    // GameRepository
    single { GameRepository(apiService = get(), favoriteGameDao = get()) }

    // GameListViewModel
    viewModel { GameListViewModel(repository = get()) }
}
