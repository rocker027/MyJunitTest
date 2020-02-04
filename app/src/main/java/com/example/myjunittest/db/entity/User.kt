package com.example.myjunittest.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = User.TABLE_NAME)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String = "",

    val age: Int = 0) {
    companion object {
        const val TABLE_NAME = "user"
    }
}