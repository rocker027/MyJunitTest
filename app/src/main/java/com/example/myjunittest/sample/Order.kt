package com.example.myjunittest.sample

class Order {
    fun insertOrder(email: String, quantity: Int, price: Int, emailUtil: IEmailUtil) {
        val weather = Weather()
        val umbrella = Umbrella()
        umbrella.getTotalPrice(weather, quantity, price)
        emailUtil.sendCustomer(email)
    }
}
