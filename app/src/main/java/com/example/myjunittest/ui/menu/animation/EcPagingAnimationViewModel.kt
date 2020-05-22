package com.example.myjunittest.ui.menu.animation

import androidx.lifecycle.MutableLiveData
import com.example.myjunittest.api.GankData
import com.example.myjunittest.api.GankRes
import com.example.myjunittest.base.BaseViewModel
import com.example.myjunittest.ui.menu.animation.EcPagingAnimationFragment.UiStatus
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.awaitResponseResult
import com.github.kittinunf.fuel.moshi.moshiDeserializerOf
import com.github.kittinunf.result.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EcPagingAnimationViewModel : BaseViewModel() {

    val fragmentLiveData = MutableLiveData<UiStatus>()

    lateinit var gankDatas: MutableList<GankData>

    fun callGankApiData() {
        val url = "https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/10"

        mainScope.launch(Dispatchers.Default) {
            val responseResult =
                Fuel.get(url).awaitResponseResult(moshiDeserializerOf(GankRes::class.java)).third

            when (responseResult) {
                is Result.Failure -> {
                }
                is Result.Success -> {
                    gankDatas = responseResult.value.data as MutableList<GankData>

                    mainScope.launch {
                        fragmentLiveData.value = UiStatus.UpdateGankData
                    }
                }
            }
        }

    }

}
