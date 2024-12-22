package com.example.services.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.services.JWTService
import io.ktor.server.auth.jwt.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.*

@Serializable
data class Payload(val aud: String, val iss: String, val username: String, val exp: Long)

class DefaultJWTService: JWTService {
    override fun createAccessToken(login: String) : String{
        return JWT.create()
            .withAudience("audience")
            .withIssuer("issuer")
            .withClaim("username", login)
            .withExpiresAt(Date(System.currentTimeMillis() + 1_000_000))
            .sign(Algorithm.HMAC256("secret"))
    }
    override fun extractUsername(token: String): String? {
        try {
            val parts = token.split(".")
            val charset = charset("UTF-8")
            val payloadString = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
            val answer = Json.decodeFromString(Payload.serializer(), payloadString).username
            return answer
        }
        catch (e: Exception) {
            return null
        }
    }
}