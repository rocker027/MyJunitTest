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

class EcPagingAnimationCartViewModel : BaseViewModel() {

    val fragmentLiveData = MutableLiveData<UiStatus>()

    lateinit var gankDatas: MutableList<GankData>


}
