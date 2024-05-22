package com.example.rawggamelistapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rawggamelistapp.data.GameRepository
import com.example.rawggamelistapp.data.database.FavoriteGameDao
import com.example.rawggamelistapp.data.remote.RawgApiService

class GameListViewModelFactory(private val apiService: RawgApiService, private val favoriteGameDao: FavoriteGameDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameListViewModel(GameRepository(apiService, favoriteGameDao)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
