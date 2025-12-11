package com.tugbaolcer.clonex.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateSessionRequest(
    @field:Json(name = "request_token") val requestToken: String
)
