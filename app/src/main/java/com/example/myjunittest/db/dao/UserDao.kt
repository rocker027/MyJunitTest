package com.example.myjunittest.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myjunittest.db.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAllUsers() : List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User) : Long

    @Query("SELECT * FROM user WHERE name = (:name)")
    fun queryByName(name : String) : User
}