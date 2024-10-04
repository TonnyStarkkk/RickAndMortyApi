package com.example.rickandmortyapi.api

import com.example.rickandmortyapi.models.ResponseApi
import com.example.rickandmortyapi.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.END_POINT)
    suspend fun getAllCharacters(
        @Query("page") page: Int
    ): Response<ResponseApi>
}