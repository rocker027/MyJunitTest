package com.example.myjunittest.api

import com.google.gson.Gson
import retrofit2.Response

sealed class ApiResult<out T : Any> {
    class Success<out T : Any>(val data: T) : ApiResult<T>()
    class Error(val error: String?, val message: String?) : ApiResult<Nothing>()
}