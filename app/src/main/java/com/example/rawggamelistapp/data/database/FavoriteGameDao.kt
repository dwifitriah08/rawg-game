package com.example.rawggamelistapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rawggamelistapp.data.model.FavoriteGame

@Dao
interface FavoriteGameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(game: FavoriteGame): Long

    @Query("DELETE FROM favorite_games WHERE id = :id")
    fun deleteFavorite(id: Int)

    @Query("SELECT * FROM favorite_games WHERE id = :id")
    fun getFavoriteById(id: Int): FavoriteGame?

    @Query("SELECT * FROM favorite_games")
    fun getAllFavorites(): List<FavoriteGame>
}
