package com.example.myjunittest

class Order {
    fun insertOrder(email: String, quantity: Int, price: Int, emailUtil: IEailUtil) {
        val weather = Weather()
        val umbrella = Umbrella()
        umbrella.getTotalPrice(weather, quantity, price)
        emailUtil.sendCustomer(email)
    }
}
