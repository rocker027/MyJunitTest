package com.example.myjunittest

import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class UmbrellaTest {

    @Test
    fun totalPriceWithSunnyday() {
        val umbrella = Umbrella()
        // mock()
        val weather = mock(IWeather::class.java)
        // use mock.when to return true or false
        `when`(weather.isSunny()).thenReturn(true)
        val expected = 200
        val actual = umbrella.getTotalPrice(weather, 2, 100)
        assertEquals(expected, actual)
    }

    @Test
    fun totalPriceWithRainingday() {
        val umbrella = Umbrella()
        // mock()
        val weather = mock(IWeather::class.java)
        // use mock.when to return true or false
        `when`(weather.isSunny()).thenReturn(false)
        val expected = 180
        val actual = umbrella.getTotalPrice(weather, 2, 100)
        assertEquals(expected, actual)
    }

}