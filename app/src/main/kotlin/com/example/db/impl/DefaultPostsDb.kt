package com.example.db.impl

import com.example.db.PostsDb
import com.example.model.Post

import java.time.Instant

import kotlin.collections.HashMap
import kotlin.random.Random

class DefaultPostsDb: PostsDb {

    private val posts: HashMap<Int, Post> = HashMap<Int, Post> ()

    override fun getAll(): Collection<Post> {
        return posts.values.toList()
    }

    override fun hasId(id: Int): Boolean {
        return posts.containsKey(id)
    }

    override fun getById(id: Int): Post? {
        return posts[id]
    }

    override fun dropId(id: Int) {
        posts.remove(id)
    }

    override fun updateById(id: Int, newText: String) {
        posts[id]?.contents = newText
        posts[id]?.changeDate = Instant.now().toString()
    }

    override fun createPost(postText: String, author: String): Post {
        val curTime = Instant.now().toString()
        val newId = Random.nextInt()
        val createdPost = Post(
            id = newId,
            contents = postText,
            creationDate = curTime,
            changeDate = curTime,
            author = author,
        )
        posts[newId] = createdPost
        return createdPost
    }
}