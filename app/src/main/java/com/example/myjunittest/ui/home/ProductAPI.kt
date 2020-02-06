package com.example.myjunittest.ui.home

import android.os.Handler

interface IProductAPI {
    interface ProductDataCallback {
        fun onGetResult(productResponse: ProductResponse)
    }

    fun getProduct(productId: String, productDataCallback: ProductDataCallback)
}


class ProductAPI : IProductAPI {
    override fun getProduct(
        productId: String,
        productDataCallback: IProductAPI.ProductDataCallback
    ) {
        val handler = Handler()
        handler.postDelayed(
            {
                val productResponse = ProductResponse(
                    id = "pixl3",
                    name = "Google Pixel3",
                    desc = "5.5 size",
                    price = 2700
                )
                productDataCallback.onGetResult(productResponse)
            }, 1000
        )
    }

}

data class ProductResponse(
    var id: String,
    var name: String,
    var desc: String,
    var price: Int = 0
)