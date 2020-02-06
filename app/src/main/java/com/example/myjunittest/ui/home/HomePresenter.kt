package com.example.myjunittest.ui.home

class HomePresenter(
    private val view: HomeContract.IHomeView,
    private val productRepository: HomeRepository
) : HomeContract.IHomePresenter {

    override fun buy(productId: String, numbers: Int) {
        productRepository.buy(productId, numbers, object : IHomeRepository.BuyProductCallback {
            override fun onBuyResult(isSuccess: Boolean) {
                if (isSuccess) {
                    view.onBuySuccess()
                } else {
                    view.onBuyFail()
                }
            }
        })
    }

    override fun getProduct(productId: String) {
        productRepository.getProduct(productId, object : IHomeRepository.LoadProductCallback {
            override fun onProductResult(productResponse: ProductResponse) {
                view.onGetResult(productResponse)
            }
        })
    }
}
