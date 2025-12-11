package com.tugbaolcer.clonex.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetSessionResponse(
    @field:Json(name = "success") val success: Boolean,
    @field:Json(name = "session_id") val sessionId: String
)
