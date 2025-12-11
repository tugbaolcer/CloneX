package com.tugbaolcer.clonex.network

import com.tugbaolcer.clonex.model.CreateLoginRequest
import com.tugbaolcer.clonex.model.CreateSessionRequest
import com.tugbaolcer.clonex.model.GetGenresResponse
import com.tugbaolcer.clonex.model.GetLoginResponse
import com.tugbaolcer.clonex.model.GetSessionResponse
import com.tugbaolcer.clonex.model.GetTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AppApi {

    @GET("authentication/token/new")
    suspend fun createRequestToken(): Response<GetTokenResponse>


    @POST("authentication/token/validate_with_login")
    suspend fun createLogin(@Body body: CreateLoginRequest): Response<GetLoginResponse>

    @POST("authentication/session/new")
    suspend fun createSessionId(
        @Body body: CreateSessionRequest
    ): Response<GetSessionResponse>

    @GET("genre/movie/list")
    suspend fun fetchGenreMovieList(): Response<GetGenresResponse>
}