package com.erezes.fnder.retrofit.data

data class ReIssueRequest(
    val refreshToken: String
)

data class ReIssueResponse(
    val `data`: Data,
    val status: Int,
    val message: String
) {
    data class Data(
        val accessToken: String,
        val refreshToken: String
    )
}
