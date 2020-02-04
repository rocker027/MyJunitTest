package com.example.myjunittest

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myjunittest.db.AppDatabase
import com.example.myjunittest.db.dao.UserDao
import com.example.myjunittest.db.entity.User
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UserTableTest {
    lateinit var db : AppDatabase
    lateinit var userDao: UserDao
    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = db.userDao()
        userDao.insertUser(User(name="Tim" , age = 30))
        userDao.insertUser(User(name="ABC" , age = 33))
    }

    @After
    fun close() {
        db.close()
    }

    @Test
    fun insertTest() {
        assertEquals(2 , userDao.getAllUsers().size)
    }

    @Test
    fun queryEmpty() {
        assertNull(userDao.queryByName("BB"))
    }
}
