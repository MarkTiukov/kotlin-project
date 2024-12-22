package com.example.services

interface JWTService {
    fun createAccessToken(login: String): String
    fun extractUsername(token: String): String?
}