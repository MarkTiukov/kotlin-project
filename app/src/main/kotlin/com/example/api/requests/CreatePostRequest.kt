package com.example.api.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val postText: String,
    val token: String
)