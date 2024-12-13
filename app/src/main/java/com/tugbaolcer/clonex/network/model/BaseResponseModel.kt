package com.tugbaolcer.clonex.network.model

import com.squareup.moshi.Json

data class BaseResponseModel<K>(
    @field:Json(name = "success")
    val isSuccessful: Boolean,
    @field:Json(name = "errorMessage")
    var errorMessage: String?,
    val `data`: K
) {
    data class ResponseErrors(
        val errors: List<String>,
        val isShow: Boolean
    )
}