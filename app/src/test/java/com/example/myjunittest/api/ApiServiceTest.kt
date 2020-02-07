package com.example.myjunittest.api

import com.example.myjunittest.ui.home.ProductResponse
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class ApiServiceTest {

    lateinit var  apiService : ApiService

    @Before
    fun setUp() {
        apiService = ApiService(BaseInterceptor())
    }

    @Test
    fun testApi() {
        val blockingGet = apiService.serviceAPI.getProduct()
            .subscribeOn(Schedulers.io())
            .blockingGet()

        assertEquals("pixel3",blockingGet.body()?.id)
        assertEquals("Google Pixel 3",blockingGet.body()?.name)
        assertEquals("5.5吋全螢幕",blockingGet.body()?.desc)
        assertEquals(27000,blockingGet.body()?.price)
//            .subscribe({ response: Response<ProductResponse>?, throwable: Throwable? -> println(response?.body().toString()) })
    }

    @Test
    fun testApi2() {
        val testObserver = TestObserver<Response<ProductResponse>>()
        apiService.serviceAPI.getProduct()
            .subscribeOn(Schedulers.trampoline())
            .subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertResult(Response.success(ProductResponse(id="1",name = "est",price = 2,desc = "tes")))

//        testObserver.assertValue("pixel3",blockingGet.body()?.id)
//        assertEquals("Google Pixel 3",blockingGet.body()?.name)
//        assertEquals("5.5吋全螢幕",blockingGet.body()?.desc)
//        assertEquals(27000,blockingGet.body()?.price)
//            .subscribe({ response: Response<ProductResponse>?, throwable: Throwable? -> println(response?.body().toString()) })
    }
}