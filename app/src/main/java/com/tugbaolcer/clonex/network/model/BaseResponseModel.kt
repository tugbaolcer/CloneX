package com.tugbaolcer.clonex.network.model

data class BaseResponseModel<K>(
    val isSuccessful: Boolean,
    var errorMessage: String?,
    val `data`: K
) {
    data class ResponseErrors(
        val errors: List<String>,
        val isShow: Boolean
    )
}