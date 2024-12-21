package com.example.db

import com.example.model.Post

interface PostsDb {

    fun getAll(): Collection<Post>
    fun hasId(id: Int): Boolean
    fun getById(id: Int): Post?
    fun dropId(id: Int)
    fun updateById(id: Int, newText: String)
    fun createPost(postText: String): Post
}