package com.example.myjunittest.base

import com.example.myjunittest.api.ApiResult
import retrofit2.Response

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<out T>
    ): ApiResult<Any> {
        return safeApiResult(call)
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<out T>
    ): ApiResult<Any> {
        try {
            val response: Response<out T> = call.invoke()
            return if (response.isSuccessful) {
                // call api success
                ApiResult.Success(
                    data = response.body()!!
                )
            } else {
                // call api error (not code 200)
                ApiResult.Error(
                    error = response.errorBody().toString(),
                    message = null
                )
            }
        } catch (e: java.lang.Exception) {
            // call api error exception
            return ApiResult.Error(error = null, message = e.message)
        }
    }
}