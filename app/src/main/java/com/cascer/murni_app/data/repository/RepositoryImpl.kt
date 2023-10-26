package com.cascer.murni_app.data.repository

import com.cascer.murni_app.data.db.UserDao
import com.cascer.murni_app.data.db.entity.UserEntity
import com.cascer.murni_app.data.model.User
import com.cascer.murni_app.data.model.mapper.Mapper.emptyUserModel
import com.cascer.murni_app.data.model.mapper.Mapper.toModel
import com.cascer.murni_app.utils.PasswordUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val userDao: UserDao, private val ioDispatcher: CoroutineDispatcher
) : Repository {
    override suspend fun register(username: String, password: String, age: Int): Boolean {
        return withContext(ioDispatcher) {
            val encryptPassword = PasswordUtils.encrypt(password)
            val insert = userDao.insert(UserEntity(username, encryptPassword, age))
            insert > -1
        }
    }

    override suspend fun edit(username: String, password: String, age: Int): Boolean {
        return withContext(ioDispatcher) {
            val encryptPassword = PasswordUtils.encrypt(password)
            val edit = userDao.edit(UserEntity(username, encryptPassword, age))
            edit > -1
        }
    }

    override suspend fun login(username: String, password: String): Boolean {
        return withContext(ioDispatcher) {
            val user = userDao.user(username)
            if (user?.username?.isNotEmpty() == true) {
                PasswordUtils.decrypt(user.password.orEmpty()) == PasswordUtils.decrypt(user.password.orEmpty())
            } else false
        }
    }

    override suspend fun user(username: String): User {
        return withContext(ioDispatcher) {
            userDao.user(username)?.toModel() ?: emptyUserModel()
        }
    }
}