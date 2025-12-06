package com.tugbaolcer.clonex.network

import com.tugbaolcer.clonex.model.CreateLoginRequest
import com.tugbaolcer.clonex.model.GetGenresResponse
import com.tugbaolcer.clonex.model.GetLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppApi {

    @GET("3/authentication")
    suspend fun fetchToken(): Response<Any>

    @POST("authentication/token/validate_with_login")
    suspend fun createLogin(@Body body: CreateLoginRequest): Response<GetLoginResponse>

    @GET("genre/movie/list")
    suspend fun fetchGenreMovieList(): Response<GetGenresResponse>
}