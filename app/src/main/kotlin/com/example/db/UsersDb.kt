package com.example.db

import com.example.model.User

interface UsersDb {
    fun getByLogin(login: String): User?
    fun hasLogin(login: String): Boolean
    fun createUser(login: String, password: String, name: String): User?
}
