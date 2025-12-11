package com.tugbaolcer.clonex.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetLoginResponse(
    @field:Json(name = "success") val isSuccess: Boolean,
    @field:Json(name = "request_token") val requestToken: String
)