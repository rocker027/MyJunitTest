package com.example.myjunittest.ui.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myjunittest.ui.home.HomeRepository
import com.example.myjunittest.ui.home.IHomeRepository
import com.example.myjunittest.ui.home.ProductResponse
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

class DashboardViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var repository: IHomeRepository

    lateinit var response: ProductResponse

    lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this,relaxUnitFun = true)

        response = ProductResponse(       id = "pixel3",
                name = "Google Pixel 3",
                price = 27000,
                desc = "TESt")

        viewModel = DashboardViewModel(repository)
    }

    @Test
    fun getProduct() {
        val productId = "pixel3"
        viewModel.getProduct(productId)

        val callbackslot = slot<IHomeRepository.LoadProductCallback>()

        verify { repository.getProduct(productId,capture(callbackslot)) }

        callbackslot.captured.onProductResult(response)

        assertEquals(response.name , viewModel.productName.value)
        assertEquals(response.desc , viewModel.productDesc.value)
        assertEquals(response.price , viewModel.productPrice.value)
    }

    @Test
    fun buySuccess() {
        val productId = "pixel3"
        val numbers = 1
        viewModel.productId.value = productId
        viewModel.productItems.value = numbers.toString()

        viewModel.buy()

        val callbackSlot = slot<IHomeRepository.BuyProductCallback>()

        verify { repository.buy(productId, numbers, capture(callbackSlot)) }

        callbackSlot.captured.onBuyResult(true)

        assertTrue(viewModel.buySuccessText.value == "購買成功")
    }

    @Test
    fun buyFail() {
        val productId = "pixel3"
        val numbers = 12
        viewModel.productId.value = productId
        viewModel.productItems.value = numbers.toString()

        viewModel.buy()

        val callbackSlot = slot<IHomeRepository.BuyProductCallback>()

        verify { repository.buy(productId, numbers, capture(callbackSlot)) }

        callbackSlot.captured.onBuyResult(false)

        assertTrue(viewModel.alertText.value == "購買失敗")
    }
}