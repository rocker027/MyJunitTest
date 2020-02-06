package com.example.myjunittest.ui.home

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class HomeRepositoryTest {

    /**
    當我們在測試Presenter，只關注：
    1.是否呼叫Repository
    2.本身的邏輯是否正確
    3.是否呼叫正確的View Callback
     */

    private lateinit var homeRepository: HomeRepository

    private lateinit var productResponse: ProductResponse

    @RelaxedMockK
    lateinit var productAPI : ProductAPI

    @RelaxedMockK
    lateinit var loadProductCallback : IHomeRepository.LoadProductCallback

    @Before
    fun setUp() {
        MockKAnnotations.init(this ,relaxUnitFun = true)

        homeRepository = HomeRepository(productAPI)

        productResponse = ProductResponse(
            id = "pixel3",
            name = "google",
            desc = "test",
            price = 270000
        )
    }

    @Test
    fun getProduct() {
        val productId = "pixel3"
        homeRepository.getProduct(productId, loadProductCallback)

        val callbackCapture = slot<IProductAPI.ProductDataCallback>()

        verify {
            productAPI.getProduct(productId,capture(callbackCapture))
        }

        callbackCapture.captured.onGetResult(productResponse)

        verify {
            callbackCapture.captured.onGetResult(productResponse)
        }
    }
}