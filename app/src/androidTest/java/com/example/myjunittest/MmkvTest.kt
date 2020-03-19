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
class MmkvTest {
    lateinit var kv : MMKV


    @Before
    fun setup() {
        val contect = InstrumentationRegistry.getInstrumentation().targetContext
        MMKV.initialize(contect)
        kv = MMKV.defaultMMKV()
    }


    @Test
    fun testSavePrefs() {
        val key = "USER_ID"
        val value = "A1234567"

        var startTime = System.currentTimeMillis()
        for (i in 1..10000) {
            kv.encode(key+i,value)
        }
        val endTime = System.currentTimeMillis()
        println("write time ${endTime - startTime}")
//        assertEquals(value , valueFromSP)
    }

    @Test
    fun testGetPrefs() {
//        println(kv.allKeys().size)
        val key = "USER_ID6666"
        println("kv size = ${kv.count()}")
        println("$key = ${kv.decodeString(key)}")
    }
}