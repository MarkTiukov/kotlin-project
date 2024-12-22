package com.example.plugins

import com.example.api.requests.*
import com.example.api.responses.AuthResponse
import com.example.db.PostsDb
import com.example.db.UsersDb
import com.example.db.impl.DefaultPostsDb
import com.example.db.impl.DefaultUsersDb
import com.example.services.impl.DefaultJWTService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*



fun Application.configureRouting() {
    val values: PostsDb = DefaultPostsDb()
    val users: UsersDb = DefaultUsersDb()
    values.createPost("example", "unknown")
    val tokenService = DefaultJWTService()

    routing {
        post("/registration") {
            val request = call.receive<RegistrationRequest>()
            val result = users.createUser(request.login, request.password, request.name)
            result ?: call.respond(HttpStatusCode.Conflict)
            call.respond(HttpStatusCode.OK)
        }

        post("/login") {
            val request = call.receive<LoginRequest>()
            if (!users.hasLogin(request.login)) {
                call.respond(HttpStatusCode.Unauthorized)
            }
            val correctPassword = users.getByLogin(request.login)?.password!!
            if (correctPassword == request.password) {
                val token = tokenService.createAccessToken(request.login)
                call.respond(AuthResponse(token))
            } else {
                call.respond(HttpStatusCode.Unauthorized)
            }

        }
        get("/") {
            call.respondText("Hello, world!")
        }

        get("/posts/all") {
            val comments = values.getAll()
            call.respond(comments)
        }

        get("/posts/{id}") {
            var id = call.parameters["id"]?.toInt()
            if (id == null) {
                call.respond(HttpStatusCode.NotFound)
            }
            id = id!!
            if (values.hasId(id)) {
                call.respond(values.getById(id)!!)
            }
            else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        authenticate("auth-jwt") {
            put("/posts/create") {
                val request = call.receive<CreatePostRequest>()
                if (request.postText.length > 500) {
                    call.respond(HttpStatusCode.PreconditionFailed)
                }
                val username = tokenService.extractUsername(request.token)
                username ?: call.respond(HttpStatusCode.BadRequest)
                val createdPost = values.createPost(request.postText, username!!)
                call.respond(createdPost)
            }
            patch("/posts/update") {
                val request = call.receive<UpdatePostRequest>()
                if (!values.hasId(request.id)) {
                    call.respond(HttpStatusCode.NotFound)
                }
                val username = tokenService.extractUsername(request.token)
                username ?: call.respond(HttpStatusCode.Unauthorized)
                if (username != values.getById(request.id)?.author) {
                    call.respond(HttpStatusCode.Forbidden)
                }
                if (request.postText.length > 500) {
                    call.respond(HttpStatusCode.PreconditionFailed)
                }

                values.updateById(request.id, request.postText)
                call.respond(values.getById(request.id)!!)
            }
            delete("/posts/delete") {
                val request = call.receive<DeletePostRequest>()
                if (!values.hasId(request.id)) {
                    call.respond(HttpStatusCode.NotFound)
                }
                val username = tokenService.extractUsername(request.token)
                username ?: call.respond(HttpStatusCode.Unauthorized)
                if (username != values.getById(request.id)?.author) {
                    call.respond(HttpStatusCode.Forbidden)
                }
                values.dropId(request.id)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}


