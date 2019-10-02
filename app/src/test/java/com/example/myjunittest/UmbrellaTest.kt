package com.example.myjunittest

import org.junit.Assert.*
import org.junit.Test

class UmbrellaTest{


    @Test
    fun totalPrice() {
        val umbrella = Umbrella()
        val weather = Weather()
        weather.isSunnyStatus = false
        val expected = 90
        val actual = umbrella.getTotalPrice(weather, 2,100)
        assertEquals(expected, actual)

    }

}