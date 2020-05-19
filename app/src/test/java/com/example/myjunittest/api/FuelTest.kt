package com.example.myjunittest.api

import com.example.myjunittest.ui.home.ProductResponse
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.github.kittinunf.result.Result.Failure
import com.github.kittinunf.result.Result.Success
import org.junit.Test

class FuelTest {
    private val productUrl =
        "https://firebasestorage.googleapis.com/v0/b/phoneauth-e70bb.appspot.com/o/product.json?alt=media&token=c051df05-399a-42af-b60f-b5430643d78e"

    @Test
    fun testFuel() {
        productUrl
            .httpGet()
            .responseString { request, response, result ->
                when (result) {
                    is Failure -> {
                        println("failure")
                        val ex = result.getException()
                        println(ex)
                    }
                    is Success -> {
                        println("success")
                        val data = result.get()
                        println(data)
                    }
                }
            }.join()
    }

    @Test
    fun testMoshi() {
        productUrl
            .httpGet()
            .responseObject(moshiDeserializerOf(ProductResponse::class.java)) { request, response, result ->
                println("req \n $request")
                println("res \n $response")
                println("result \n $result")

                when (result) {
                    is Failure -> {
                        println("failure")
                        val ex = result.getException()
                        println(ex)
                    }
                    is Success -> {
                        println("success")
                        val data = result.get()
                        println(data)

                    }
                }
            }.join()
    }

    @Test
    fun testString() {
        println("this is test")
    }
}