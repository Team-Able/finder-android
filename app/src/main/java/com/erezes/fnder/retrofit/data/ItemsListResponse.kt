package com.erezes.fnder.retrofit.data

data class ItemsListResponse(
    val data: List<Data>,
    val message: String,
    val status: Int
) {
    data class Data(
        val content: String,
        val createdAt: String,
        val id: Int,
        val imageUrl: String,
        val location: Location,
        val status: String,
        val title: String,
        val updatedAt: String
    ) {
        data class Location(
            val latitude: Int,
            val longitude: Int
        )
    }
}