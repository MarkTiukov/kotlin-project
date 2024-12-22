package com.example.db.impl

import com.example.db.UsersDb
import com.example.model.User

import java.time.Instant

import kotlin.collections.HashMap

class DefaultUsersDb: UsersDb {

    private val users: HashMap<String, User> = HashMap<String, User> ()

    override fun getByLogin(login: String): User? {
        return users[login]
    }

    override fun hasLogin(login: String): Boolean{
        return users.containsKey(login)
    }

    override fun createUser(login: String, password: String, name: String): User? {
        if (users.containsKey(login)) {
            return null
        }
        val curTime = Instant.now().toString()
        val createdUser = User(
            login = login,
            password = password,
            name = name,
            creationDate = curTime,
        )
        users[login] = createdUser
        return createdUser
    }

}