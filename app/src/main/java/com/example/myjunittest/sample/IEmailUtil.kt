package com.example.myjunittest.sample

interface IEmailUtil {
    fun sendCustomer(email: String)
}

class MockEmailUtil : IEmailUtil {
    var receiverEmail : String? = null
    override fun sendCustomer(email: String) {
        receiverEmail = email
    }

}