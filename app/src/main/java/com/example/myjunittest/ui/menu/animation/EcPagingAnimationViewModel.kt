package com.example.myjunittest.ui.menu.animation

import androidx.lifecycle.MutableLiveData
import com.example.myjunittest.api.GankData
import com.example.myjunittest.api.GankRes
import com.example.myjunittest.base.BaseViewModel
import com.example.myjunittest.ui.menu.animation.EcPagingAnimationFragment.UiStatus
import com.github.kittinunf.fuel.httpGet
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
            url.httpGet()
                .responseObject(moshiDeserializerOf(GankRes::class.java)) { _, _, result ->
                    when (result) {
                        is Result.Failure -> {
                        }
                        is Result.Success -> {
                            println("gank size -> ${result.get().data.size}")
                            gankDatas = result.get().data as MutableList<GankData>

                            mainScope.launch {
                                fragmentLiveData.value = UiStatus.UpdateGankData
                            }
                        }
                    }
                }
        }

    }

}
