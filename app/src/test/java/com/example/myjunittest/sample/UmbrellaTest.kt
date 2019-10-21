package com.example.myjunittest.sample

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UmbrellaTest {
    @MockK
    lateinit var weather: IWeather

    lateinit var umbrella: Umbrella

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        umbrella = Umbrella()
    }

    @Test
    fun totalPriceWithSunnyday() {
        // use mock.when to return true or false
        every { weather.isSunny() } returns true
        val expected = 200
        val actual = umbrella.getTotalPrice(weather, 2, 100)
        assertEquals(expected, actual)
    }

    @Test
    fun totalPriceWithRainingday() {
        // use mock.when to return true or false
        every { weather.isSunny() } returns false
        val expected = 180
        val actual = umbrella.getTotalPrice(weather, 2, 100)
        assertEquals(expected, actual)
    }

}