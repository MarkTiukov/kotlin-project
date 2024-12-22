package com.example.api.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse (val accessToken: String)