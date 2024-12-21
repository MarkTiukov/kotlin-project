package com.example.api.model

import kotlinx.serialization.Serializable

@Serializable
data class DeletePostRequest(
    val id: Int,
)