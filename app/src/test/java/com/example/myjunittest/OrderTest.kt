package com.example.myjunittest

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class OrderTest {

    @Test
    fun insertOrder() {
        val order  = Order()
        // mock()
        val mockEmailUtil = mock(IEailUtil::class.java)
        val userEmail = "customer@com.tw"
        order.insertOrder(userEmail,1,100,mockEmailUtil)

        // to verify mock object has call sendCustomer function
        verify(mockEmailUtil).sendCustomer(userEmail)
    }
}