package com.cascer.murni_app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cascer.murni_app.data.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity): Long

    @Query("SELECT * FROM user ORDER BY username ASC")
    fun users(): List<UserEntity>

    @Query("SELECT * FROM user WHERE username = :username")
    fun user(username: String): UserEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun edit(user: UserEntity): Int
}