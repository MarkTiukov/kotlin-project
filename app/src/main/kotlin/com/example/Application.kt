package com.example

import com.example.plugins.configureAuth
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

import com.example.plugins.configureRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, module=Application::module).start(wait = true)
}
fun Application.module() {
    install(ContentNegotiation) {
        json(Json{
            ignoreUnknownKeys= true
        })
    }
    configureAuth()
    configureRouting()
}
