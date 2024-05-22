package com.example.rawggamelistapp.data.model

data class RawgResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Game>,
    val user_platforms: Boolean
)