package com.example.myjunittest.ui.register

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RegisterVerifyTest {
    @MockK
    lateinit var registerVerify: RegisterVerify

    @Before
    fun setup() {
        MockKAnnotations.init(this,relaxed = true)
    }

    @Test
    fun verifyLoginIdTrue() {
        assertTrue(registerVerify.verityUserId("A123456"))
    }

    @Test
    fun verifyLoginIdFalse() {
        assertFalse(registerVerify.verityUserId("A1234"))
    }

    @Test
    fun verifyUserPasswordTrue(){
        assertTrue(registerVerify.verifyUserPassword("A1231545"))
    }

    @Test
    fun verifyUserPasswordFalseByLength() {
        assertFalse(registerVerify.verifyUserPassword("A545"))
    }

    @Test
    fun verifyUserPasswordFalseByAllLatter() {
        assertFalse(registerVerify.verifyUserPassword("AAAAAAAAA"))
    }

    @Test
    fun verifyUserPasswordFalseByNotLatterDigit() {
        assertFalse(registerVerify.verifyUserPassword("        "))
    }

    @Test
    fun verifyClickBtnRegisterTrue() {
        assertTrue(registerVerify.verifyRegisterInfo(userId = "A123456", userPassword = "A1234567"))
    }

    @Test
    fun verifyClickBtnRegisterFalse() {
        assertFalse(registerVerify.verifyRegisterInfo(userId = "A1234", userPassword = "A1234567"))
    }
}