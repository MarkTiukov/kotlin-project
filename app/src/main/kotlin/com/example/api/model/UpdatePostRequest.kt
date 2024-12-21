package com.example.api.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePostRequest(
    val id: Int,
    val postText: String
)