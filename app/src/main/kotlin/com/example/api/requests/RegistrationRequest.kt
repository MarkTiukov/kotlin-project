package com.example.api.requests

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
    val login: String,
    val password: String,
    val name: String,
)