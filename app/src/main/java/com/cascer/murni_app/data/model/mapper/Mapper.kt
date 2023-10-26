package com.cascer.murni_app.data.model.mapper

import com.cascer.murni_app.data.db.entity.UserEntity
import com.cascer.murni_app.data.model.User

object Mapper {
    fun UserEntity.toModel() = User(
        username = username.orEmpty(),
        password = password.orEmpty(),
        age = age ?: 0
    )

    fun emptyUserModel() = User(
        username = "",
        password = "",
        age = 0
    )
}