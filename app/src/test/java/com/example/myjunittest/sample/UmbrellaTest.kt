package com.example.myjunittest.sample

import com.example.myjunittest.sample.IWeather
import com.example.myjunittest.sample.Umbrella
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class UmbrellaTest {
    @Mock
    lateinit var weather: IWeather

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun totalPriceWithSunnyday() {
        val umbrella = Umbrella()
        // use mock.when to return true or false
        `when`(weather.isSunny()).thenReturn(true)
        val expected = 200
        val actual = umbrella.getTotalPrice(weather, 2, 100)
        assertEquals(expected, actual)
    }

    @Test
    fun totalPriceWithRainingday() {
        val umbrella = Umbrella()
        // use mock.when to return true or false
        `when`(weather.isSunny()).thenReturn(false)
        val expected = 180
        val actual = umbrella.getTotalPrice(weather, 2, 100)
        assertEquals(expected, actual)
    }

}