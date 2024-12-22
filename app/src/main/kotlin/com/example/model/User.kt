package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class User(val login: String, val password: String, val name: String, val creationDate: String)