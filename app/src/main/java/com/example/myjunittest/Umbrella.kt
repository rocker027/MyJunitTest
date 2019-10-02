package com.example.myjunittest

class Umbrella {

    /**
     * get total umbrella prices
     * if sunday not discount
     * if rain , the price 10% off discount
     */
    fun getTotalPrice(weather: IWeather, quantity: Int, price: Int): Int {
        return if (weather.isSunny()) {
            quantity * price
        } else {
            quantity * (price * 0.9).toInt()
        }
    }
}