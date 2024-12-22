package com.example.api.requests

import kotlinx.serialization.Serializable

@Serializable
data class DeletePostRequest(
    val id: Int,
    val token: String
)