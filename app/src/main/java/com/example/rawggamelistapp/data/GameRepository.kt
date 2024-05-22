package com.example.rawggamelistapp.data

import com.example.rawggamelistapp.data.database.FavoriteGameDao
import com.example.rawggamelistapp.data.model.FavoriteGame
import com.example.rawggamelistapp.data.model.Game
import com.example.rawggamelistapp.data.model.GameDetail
import com.example.rawggamelistapp.data.remote.RawgApiService

class GameRepository(private val apiService: RawgApiService, private val favoriteGameDao: FavoriteGameDao) {

    suspend fun getGames(): List<Game> {
        val rawgResponse = apiService.getResponse(
            key = "d1f53c2a972740b08c15137fdeaa66d9",
            dates = "2019-09-27,2019-09-30",
            platforms = "18,1,7",
            pageSize = 10,
            page = 1
        )
        return rawgResponse.results
    }

    suspend fun getGameDetail(gameId: Int): GameDetail {
        return apiService.getGameDetail(
            gameId = gameId,
            key = "d1f53c2a972740b08c15137fdeaa66d9"
        )
    }

    fun saveToDBFavorites(game: GameDetail,flagFav: Boolean) {
        val favoriteGame = FavoriteGame(
            id = game.id,
            title = game.name,
            imageUrl = game.background_image,
            description = game.description,
            releaseDate = game.released,
            genre = game.genres.joinToString(","){it.name},
            flagFav = flagFav
        )
        favoriteGameDao.insertFavorite(favoriteGame)
    }

    fun deleteFromDBFavorites(id: Int) {
        favoriteGameDao.deleteFavorite(id)
    }

    fun getFavoriteById(id: Int): FavoriteGame? {
        return favoriteGameDao.getFavoriteById(id)
    }

    fun getAllFavoriteGames(): List<FavoriteGame> {
        return favoriteGameDao.getAllFavorites()
    }
}
