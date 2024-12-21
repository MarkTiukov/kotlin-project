package com.example.plugins

import com.example.api.model.CreatePostRequest
import com.example.api.model.DeletePostRequest
import com.example.api.model.UpdatePostRequest
import com.example.db.impl.DefaultPostsDb
import com.example.db.PostsDb

import io.ktor.http.*

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    var values : PostsDb = DefaultPostsDb ()
    values.createPost("aba")

    routing {
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

        put("/posts/create") {
            val request = call.receive<CreatePostRequest>()
            if (request.postText.length > 500) {
                call.respond(HttpStatusCode.PreconditionFailed)
            }
            val createdPost = values.createPost(request.postText)
            call.respond(createdPost)
        }
        patch("/posts/update") {
            val request = call.receive<UpdatePostRequest>()
            if (!values.hasId(request.id)) {
                call.respond(HttpStatusCode.NotFound)
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
            values.dropId(request.id)
            call.respond(HttpStatusCode.OK)
        }
    }
}
