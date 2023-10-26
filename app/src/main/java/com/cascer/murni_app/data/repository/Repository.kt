package com.cascer.murni_app.data.repository

import com.cascer.murni_app.data.model.User

interface Repository {
    suspend fun register(username: String, password: String, age: Int): Boolean
    suspend fun edit(username: String, password: String, age: Int): Boolean
    suspend fun login(username: String, password: String): Boolean
    suspend fun user(username: String): User
}