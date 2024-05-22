package com.example.rawggamelistapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rawggamelistapp.data.GameRepository
import com.example.rawggamelistapp.data.model.Game
import kotlinx.coroutines.launch

class GameListViewModel(private val repository: GameRepository) : ViewModel() {

    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>>
        get() = _games

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun getGames() {
        viewModelScope.launch {
            try {
                val response = repository.getGames()
                _games.value = response
            } catch (e: Exception) {
                _error.value = "Failed to fetch games: ${e.message}"
            }
        }
    }
}
