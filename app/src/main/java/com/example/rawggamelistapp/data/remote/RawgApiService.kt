package com.example.rawggamelistapp.data.remote

import com.example.rawggamelistapp.data.model.GameDetail
import com.example.rawggamelistapp.data.model.RawgResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgApiService {

    @GET("games")
    suspend fun getResponse(
        @Query("key") key: String,
        @Query("dates") dates: String,
        @Query("platforms") platforms: String,
        @Query("page_size") pageSize: Int,
        @Query("page") page: Int
    ): RawgResponse

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") gameId: Int,
        @Query("key") key: String
    ): GameDetail


}
