package com.example.myjunittest

import android.os.Build
import junit.framework.Assert.assertEquals
import kotlinx.android.synthetic.main.fragment_register.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MainActivityTest {
    lateinit var activity: MainActivity
    @Before
    fun setUpActivity() {
        activity = Robolectric.buildActivity(MainActivity::class.java).setup().get()
    }

    /**
     * 測試user register click btn 後是否有show toast 並顯示正確文字
     */
    @Config(sdk = [Build.VERSION_CODES.O_MR1])
    @Test
    fun registerSuccessShowToast() {
        val shadowActivity = Shadows.shadowOf(activity)
        val userId = "A123456"
        val userPwd = "a12345678"
        activity.etInputUserName.setText(userId)
        activity.etInputPassword.setText(userPwd)

        activity.btnVerifyRegister.performClick()
        assertEquals(ShadowToast.getTextOfLatestToast(),"it right")
    }

    @Config(sdk = [Build.VERSION_CODES.O_MR1])
    @Test
    fun registerFailedShowToast() {
        val userId = "A1234"
        val userPwd = "a12345678"
        activity.etInputUserName.setText(userId)
        activity.etInputPassword.setText(userPwd)

        activity.btnVerifyRegister.performClick()
        assertEquals(ShadowToast.getTextOfLatestToast(),"input user Id / Password incorrect !! please check ~")
    }
}