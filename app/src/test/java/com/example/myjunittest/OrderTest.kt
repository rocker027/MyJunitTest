package com.example.myjunittest

import org.junit.Test

import org.junit.Assert.*

class OrderTest {

    @Test
    fun insertOrder() {
        val order  = Order()
        val mockEmailUtil = MockEmailUtil()
        val userEmail = "customer@com.tw"
        order.insertOrder(userEmail,1,100,mockEmailUtil)
        assertEquals(userEmail,mockEmailUtil.receiverEmail)
    }
}