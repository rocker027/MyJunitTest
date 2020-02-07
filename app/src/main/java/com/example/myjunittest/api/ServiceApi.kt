package com.example.myjunittest.api

import com.example.myjunittest.ui.home.ProductResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface ServiceApi {
    @GET("product.json?alt=media&token=c051df05-399a-42af-b60f-b5430643d78e")
    fun getProduct(): Single<Response<ProductResponse>>

//    @GET(ApiConfig.buyUrl)
//    fun buy(): Single<Response<BuyResponse>>
}