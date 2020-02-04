package com.example.myjunittest

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myjunittest.utils.ISharePreferenceManager
import com.example.myjunittest.utils.SharePreferenceManager
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
/**
 * 測試SharePreference 是否有寫入
 */
class SharePreferenceManagerTest {
    @Test
    fun testSavePrefs() {
        val contect = InstrumentationRegistry.getInstrumentation().targetContext
        val key = "USER_ID"
        val value = "A1234567"

        val sharePreferenceManager : ISharePreferenceManager = SharePreferenceManager(contect)
        sharePreferenceManager.saveString(key, value)
        val valueFromSP = sharePreferenceManager.getString(key)
        assertEquals(value , valueFromSP)
    }
}