package com.example


import com.example.plugins.AuthPlugin
import com.example.plugins.configureRouting

import io.ktor.serialization.kotlinx.json.*

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, module=Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    install(AuthPlugin)
    configureRouting()
}
