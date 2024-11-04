package com.erezes.fnder.retrofit.data

data class LogInRequest(
    val email: String,
    val password: String
)

data class LogInResponse(
    val `data`: Data,
    val message: String,
    val status: Int
) {
    data class Data(
        val accessToken: String,
        val refreshToken: String
    )
}