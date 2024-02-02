package com.appweaver.ricknroll.remote

import com.appweaver.ricknroll.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface  RickNRollApi{
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
    ): CharacterResponse
}