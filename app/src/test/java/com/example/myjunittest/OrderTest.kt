package com.example.myjunittest

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class OrderTest {
    @Mock
    lateinit var mockEmailUtil: IEmailUtil

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun insertOrder() {
        val order  = Order()
        // mock()
        val userEmail = "customer@com.tw"
        order.insertOrder(userEmail,1,100,mockEmailUtil)

        // to verify mock object has call sendCustomer function
        verify(mockEmailUtil).sendCustomer(userEmail)
        // verify 1 times
        verify(mockEmailUtil, times(1)).sendCustomer(userEmail)
        // verify cannot call function
        verify(mockEmailUtil, never()).sendCustomer(userEmail)
        // verify least 1 times
        verify(mockEmailUtil, atLeast(1)).sendCustomer(userEmail)
        // verify most 1 times
        verify(mockEmailUtil, atMost(1)).sendCustomer(userEmail)
    }
}