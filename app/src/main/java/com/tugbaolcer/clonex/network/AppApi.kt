package com.tugbaolcer.clonex.network

import com.tugbaolcer.clonex.model.GetGenresResponse
import retrofit2.Response
import retrofit2.http.GET

interface AppApi {
    @GET("genre/movie/list")
    suspend fun fetchGenreMovieList(): Response<GetGenresResponse>
}