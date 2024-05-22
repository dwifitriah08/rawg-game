package com.example.rawggamelistapp.data.model

data class Game(
    val slug: String,
    val name: String,
    val playtime: Int,
    val platforms: List<Platform>,
    val stores: List<Store>,
    val released: String,
    val tba: Boolean,
    val background_image: String,
    val rating: Double,
    val rating_top: Int,
    val ratings: List<Rating>,
    val ratings_count: Int,
    val reviews_text_count: Int,
    val added: Int,
    val added_by_status: AddedByStatus,
    val metacritic: Int?,
    val suggestions_count: Int,
    val updated: String,
    val id: Int,
    val score: Int?,
    val clip: Any?, // Change to specific type if needed
    val tags: List<Tag>,
    val esrb_rating: Any?, // Change to specific type if needed
    val user_game: Any?, // Change to specific type if needed
    val reviews_count: Int,
    val community_rating: Int,
    val saturated_color: String,
    val dominant_color: String,
    val short_screenshots: List<ShortScreenshot>,
    val parent_platforms: List<ParentPlatform>,
    val genres: List<Genre>
)