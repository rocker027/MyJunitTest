package com.example.myjunittest.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiConfig {
    const val WEB_HOST =""
    const val TIME_OUT_CONNECT = 30
    const val TIME_OUT_READ = 30
    const val TIME_OUT_WRITE = 30

    const val productUrl = "https://firebasestorage.googleapis.com/v0/b/phoneauth-e70bb.appspot.com/o/product.json?alt=media&token=c051df05-399a-42af-b60f-b5430643d78e"
    const val buyUrl = "https://firebasestorage.googleapis.com/v0/b/phoneauth-e70bb.appspot.com/o/buy.json?alt=media&token=cad7488d-e1d2-49a9-b881-abdde57cb5da"
    const val baseUrl = "https://firebasestorage.googleapis.com/v0/b/phoneauth-e70bb.appspot.com/o/"
}

class ApiService(interceptor: Interceptor){
    var serviceAPI : ServiceApi

    init{
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(ApiConfig.TIME_OUT_CONNECT.toLong(),TimeUnit.SECONDS)
            .readTimeout(ApiConfig.TIME_OUT_READ.toLong(),TimeUnit.SECONDS)
            .writeTimeout(ApiConfig.TIME_OUT_WRITE.toLong(),TimeUnit.SECONDS)
            .build()
        val refrotfit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(ApiConfig.baseUrl)
            .build()
        serviceAPI = refrotfit.create(ServiceApi::class.java)

    }

}

class BaseInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .method(original.method(), original.body())
            .build()
        return chain.proceed(request)
    }
}