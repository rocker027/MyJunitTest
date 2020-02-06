package com.example.myjunittest.ui.home

interface IHomeRepository {
    fun getProduct(productId: String, loadProductCallback: LoadProductCallback)

    fun buy(productId: String, numbers: Int, buyProductCallback: BuyProductCallback)

    interface LoadProductCallback {
        fun onProductResult(productResponse: ProductResponse)
    }

    interface BuyProductCallback {
        fun onBuyResult(isSuccess: Boolean)
    }
}

class HomeRepository(val productAPI : IProductAPI) : IHomeRepository{
    override fun getProduct(
        productId: String,
        loadProductCallback: IHomeRepository.LoadProductCallback
    ) {
        productAPI.getProduct(productId, object : IProductAPI.ProductDataCallback{
            override fun onGetResult(productResponse: ProductResponse) {
                loadProductCallback.onProductResult(productResponse)
            }
        })
    }

    override fun buy(
        productId: String,
        numbers: Int,
        buyProductCallback: IHomeRepository.BuyProductCallback
    ) {
        if (numbers <= 10) {
            buyProductCallback.onBuyResult(true)
        } else {
            buyProductCallback.onBuyResult(false)
        }
    }
}