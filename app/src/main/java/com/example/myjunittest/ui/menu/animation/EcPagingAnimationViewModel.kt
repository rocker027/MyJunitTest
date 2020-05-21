package com.example.myjunittest.ui.menu.animation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EcPagingAnimationViewModel : ViewModel() {

    val currentPositionLiveData = MutableLiveData<Int>()

    val fakeItems : MutableList<Int> = mutableListOf(1, 2, 3, 4, 5, 6)

}
