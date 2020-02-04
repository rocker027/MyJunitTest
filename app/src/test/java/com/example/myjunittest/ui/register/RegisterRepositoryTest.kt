package com.example.myjunittest.ui.register

import android.content.Context
import android.content.SharedPreferences
import com.example.myjunittest.utils.SharePreferenceManager
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class RegisterRepositoryTest {
    /**
     * 練習模擬 SharePreferences
     */
//    lateinit var sharedPrefs : SharedPreferences
//    lateinit var editor : SharedPreferences.Editor
//    lateinit var context: Context
//
//    @Before
//    fun setUp() {
//        sharedPrefs = mockk()
//        editor = mockk(relaxed = true)
//        context = mockk()
//    }

//    @Test
//    fun saveUserId() {
        // give
//        every { context.getSharedPreferences(any(),any()) } returns sharedPrefs
//        every { sharedPrefs.edit() } returns editor
//        every { editor.putString(allAny(),allAny()) } returns  editor
//
//        // when
//        val userId = "A1234567"
//        val preKey = "USER_ID"
//
//        val repository = RegisterRepository(context)
//        repository.saveUserId(userId)
//
//        // then
//        verify {
//            context.getSharedPreferences(any(),any())
//            editor.putString(any(),any())
//            editor.apply()
//        }
//    }

    @Test
    fun saveUserIdTest() {
        val mockSharePreferenceManager = mockk<SharePreferenceManager>(relaxed = true)
        val userId = "A1234567"
        val repository = RegisterRepository(mockSharePreferenceManager)
        repository.saveUserId(userId)

        verify { mockSharePreferenceManager.saveString("USER_ID",userId) }
    }
}

