package com.example.myjunittest

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myjunittest.utils.ISharePreferenceManager
import com.example.myjunittest.utils.SharePreferenceManager
import com.orhanobut.hawk.Hawk
import com.tencent.mmkv.MMKV
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
/**
 * 測試Hawk 是否有寫入
 */
class HawkTest {
    @Test
    fun testSavePrefs() {
        val contect = InstrumentationRegistry.getInstrumentation().targetContext
        val key = "USER_ID"
        val value = "A1234567"

        Hawk.init(contect).build()
        var startTime = System.currentTimeMillis()
        for (i in 1..10000) {
            Hawk.put(key+i,value)
        }
        val endTime = System.currentTimeMillis()
        println("write time ${endTime - startTime}")
//        assertEquals(value , valueFromSP)
    }

    @Test
    fun testGetPrefs() {
        val contect = InstrumentationRegistry.getInstrumentation().targetContext
        Hawk.init(contect).build()
        println(Hawk.count())
    }
}