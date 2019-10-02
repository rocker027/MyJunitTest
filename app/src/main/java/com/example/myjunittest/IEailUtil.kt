package com.example.myjunittest

interface IEailUtil {
    fun sendCustomer(email: String)
}

class MockEmailUtil : IEailUtil {
    var receiverEmail : String? = null
    override fun sendCustomer(email: String) {
        receiverEmail = email
    }

}