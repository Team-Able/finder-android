package com.erezes.fnder

import com.erezes.fnder.retrofit.data.ItemsListResponse

data class FindItem(
    val content: String,
    val createdAt: String,
    val id: Int,
    val imageUrl: String,
    val location: ItemsListResponse.Data.Location? = null,
    val status: String,
    val title: String,
    val updatedAt: String
)
