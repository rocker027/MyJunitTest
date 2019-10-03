package com.example.myjunittest.sample

interface IWeather {
    fun isSunny() : Boolean
}

class Weather : IWeather {
    var isSunnyStatus  = true

    override fun isSunny(): Boolean {
        return isSunnyStatus
    }
}