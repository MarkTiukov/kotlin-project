package com.example.api.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePostRequest(
    val id: Int,
    val postText: String,
    val token: String,
)