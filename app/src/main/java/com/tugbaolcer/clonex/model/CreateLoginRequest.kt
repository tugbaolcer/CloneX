package com.tugbaolcer.clonex.model

data class CreateLoginRequest(
    val username: String,
    val password: String,
    val requestToken: String
)