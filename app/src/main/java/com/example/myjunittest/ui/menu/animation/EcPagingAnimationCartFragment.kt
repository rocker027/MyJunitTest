package com.example.myjunittest.ui.menu.animation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.myjunittest.R
import com.example.myjunittest.base.BaseFragment
import com.google.android.material.transition.MaterialContainerTransform

/**
 * A simple [Fragment] subclass.
 * Use the [EcPagingAnimationCartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EcPagingAnimationCartFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ec_paging_animation_cart, container, false)
        // 設定 共享元素進入動畫
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 1500
        }

        return view
    }


}