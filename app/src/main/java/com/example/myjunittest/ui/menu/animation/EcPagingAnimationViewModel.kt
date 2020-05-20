package com.example.myjunittest.ui.menu.animation

import androidx.lifecycle.ViewModel

class EcPagingAnimationViewModel : ViewModel() {

    fun getFakeItems(): MutableList<Int> {
        return mutableListOf<Int>(1,2,3,4,5,6)
    }
}
