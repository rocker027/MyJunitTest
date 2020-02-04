package com.example.myjunittest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myjunittest.db.dao.UserDao
import com.example.myjunittest.db.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    public abstract fun userDao() : UserDao

    companion object {
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context:Context): AppDatabase?{
            if (INSTANCE == null) {
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        AppDatabase::class.java.simpleName
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}