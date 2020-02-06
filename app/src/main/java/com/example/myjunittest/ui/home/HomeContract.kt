package com.example.myjunittest.ui.home

class HomeContract {

    interface IHomePresenter {
        fun getProduct(productId: String)
        fun buy(productId: String, numbers: Int)
    }

    interface IHomeView {
        fun onGetResult(productResponse: ProductResponse)
        fun onBuySuccess()
        fun onBuyFail()
    }
}