package com.cascer.murni_app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "username")
    val username: String = "",
    @ColumnInfo(name = "password")
    val password: String?,
    @ColumnInfo(name = "age")
    val age: Int?
)
