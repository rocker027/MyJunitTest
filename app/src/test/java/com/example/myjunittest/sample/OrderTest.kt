package com.example.myjunittest.sample

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class OrderTest {
    @MockK(relaxUnitFun = true)
    lateinit var mockEmailUtil: MockEmailUtil

    @Before
    fun setup() = MockKAnnotations.init(this, relaxed = true)

    @Test
    fun insertOrder() {
        val order = Order()
//        var mockEmailUtil = mockk<MockEmailUtil>(relaxed = true)
//        every { mockEmailUtil.sendCustomer(any()) } just Runs
        // mock()
        val userEmail = "customer@com.tw"
        order.insertOrder(userEmail, 1, 100, mockEmailUtil)


        // to verify mock object has call sendCustomer function
        verify { mockEmailUtil.sendCustomer(any()) }

        // verify 1 times
        verify(exactly = 1) { mockEmailUtil.sendCustomer(any()) }
//        verify(mockEmailUtil, times(1)).sendCustomer(userEmail)
        // verify cannot call function
//        verify(exactly = 0) { mockEmailUtil.sendCustomer(any())  }
        // verify least 1 times
        verify(atLeast = 1) { mockEmailUtil.sendCustomer(any()) }
        // verify most 1 times
        verify(atMost = 1) { mockEmailUtil.sendCustomer(any()) }
    }
}