package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


val AuthPlugin = createApplicationPlugin(name = "AuthPlugin") {
    onCall { call ->
        println("Caught uri ${call.request.uri}")
    }
}