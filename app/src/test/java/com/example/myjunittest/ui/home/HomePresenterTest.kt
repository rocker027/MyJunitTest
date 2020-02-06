package com.example.myjunittest.ui.home

import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class HomePresenterTest {
    @RelaxedMockK
    lateinit var homeView : HomeContract.IHomeView

    @RelaxedMockK
    lateinit var repository: HomeRepository

    lateinit var productResponse : ProductResponse

    lateinit var homePresenter : HomeContract.IHomePresenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this ,relaxUnitFun = true)
        homePresenter = HomePresenter(homeView, repository)
        productResponse = ProductResponse(
            id = "pixel3",
            name = "google",
            desc = "test",
            price = 270000
        )
    }

    @Test
    fun getProductTest() {
        val productId = "pixel3"
        // call sut
        homePresenter.getProduct(productId)

        // 設定capture
        val loadProductCallbackCaptorSlot = slot<IHomeRepository.LoadProductCallback>()

        // 驗證repository.getProduct 有被呼叫
        verify {
            repository.getProduct(productId, capture(loadProductCallbackCaptorSlot))
        }

        // callback回傳 response
        loadProductCallbackCaptorSlot.captured.onProductResult(productResponse)

        // 驗證 homeView.onGetResult是否被呼叫
        verify{ homeView.onGetResult(eq(ProductResponse(id ="pixel3",name = "google",desc = "test",price = 270000))) }

    }

    @Test
    fun buySuccess() {
        val callbackCapture = slot<IHomeRepository.BuyProductCallback>()

        val productId = "piexl3"
        val number = 3

        homePresenter.buy(productId, number)

        verify {
            repository.buy(productId,number,capture(callbackCapture))
        }

        callbackCapture.captured.onBuyResult(true)

        verify {
            homeView.onBuySuccess()
        }
    }

    @Test
    fun buyFail() {
        val productId = "piexl3"
        val number = 12

        homePresenter.buy(productId,number)

        val callbackCaptor = slot<IHomeRepository.BuyProductCallback>()

        verify { repository.buy(productId,number,capture(callbackCaptor)) }

        callbackCaptor.captured.onBuyResult(false)

        verify { homeView.onBuyFail() }
    }
}