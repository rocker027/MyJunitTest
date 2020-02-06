package com.example.myjunittest.ui.dashboard

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myjunittest.ui.home.IHomeRepository
import com.example.myjunittest.ui.home.ProductResponse

class DashboardViewModel(private val repository: IHomeRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    var productId : MutableLiveData<String> = MutableLiveData()
    var productName : MutableLiveData<String> = MutableLiveData()
    var productDesc : MutableLiveData<String> = MutableLiveData()
    var productPrice : MutableLiveData<Int> = MutableLiveData()
    var productItems : MutableLiveData<String> = MutableLiveData()
    var buySuccessText : MutableLiveData<String> = MutableLiveData()
    var alertText : MutableLiveData<String> = MutableLiveData()



    fun getProduct(productId : String) {
        repository.getProduct(productId,object : IHomeRepository.LoadProductCallback {
            override fun onProductResult(response: ProductResponse) {
                productName.value = response.name
                productDesc.value = response.desc
                productPrice.value = response.price
            }
        })
    }

    fun buy() {
        val productId = productId.value ?: ""
        val numbers = (productItems.value ?: "0").toInt()

        repository.buy(productId,numbers,object : IHomeRepository.BuyProductCallback {
            override fun onBuyResult(isSuccess: Boolean) {
                if (isSuccess) {
                    buySuccessText.value = "購買成功"
                } else {
                    alertText.value = "購買失敗"
                }
            }
        })
    }
}