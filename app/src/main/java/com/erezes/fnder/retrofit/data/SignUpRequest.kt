package com.erezes.fnder.retrofit.data

data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String
)

data class SignUpResponse(
    val `data`: Any,
    val status: Int,
    val message: String
)