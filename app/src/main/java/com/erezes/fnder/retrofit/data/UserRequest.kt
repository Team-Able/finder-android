package com.erezes.fnder.retrofit.data

data class UserResponse(
    val data: UserData,
    val status: Int,
    val message: String
)

data class UserData(
    val username: String,
    val email: String
)