package com.example.rawggamelistapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_games")
data class FavoriteGame(
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String,
    val description: String,
    val releaseDate: String,
    val genre: String,
    val flagFav: Boolean
)
