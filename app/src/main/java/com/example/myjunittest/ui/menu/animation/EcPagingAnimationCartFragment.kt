package com.example.myjunittest.ui.menu.animation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import androidx.fragment.app.Fragment
import com.example.myjunittest.base.BaseFragment
import com.example.myjunittest.databinding.FragmentEcPagingAnimationCartBinding
import com.google.android.material.transition.MaterialContainerTransform


/**
 * A simple [Fragment] subclass.
 * Use the [EcPagingAnimationCartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EcPagingAnimationCartFragment : BaseFragment() {
    private lateinit var cartViewBinding: FragmentEcPagingAnimationCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cartViewBinding = FragmentEcPagingAnimationCartBinding.inflate(inflater)
        // 設定 共享元素進入動畫
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 1500
        }

        initView()


        return cartViewBinding.root
    }

    private fun initView() {
        cartViewBinding.btnClick.apply {
            setOnClickListener {
                playTo()
            }
        }

    }

    private fun playTo() {
        val trans =
            ObjectAnimator.ofFloat(cartViewBinding.imageView, "translationX", 0f, 100f, 0f, 100f)
                .apply {
                    duration = 3000
                }

        val alpha = ObjectAnimator.ofFloat(cartViewBinding.imageView, "alpha", 1f, 0f)
            .apply {
                duration = 6000
            }

        val rotation = ObjectAnimator.ofFloat(cartViewBinding.imageView, "rotationY", 0f, 360f)
            .apply {
                duration = 3000
            }


        AnimatorSet().apply {
            this.play(trans).after(rotation)
            duration = 5000
        }.start()

    }

    private fun transMoiveX() {
        ObjectAnimator.ofFloat(cartViewBinding.imageView, "translationX", 0f, 100f, 0f, 100f)
            .apply {
                duration = 3000
            }.start()
    }

    private fun alpha() {
        ObjectAnimator.ofFloat(cartViewBinding.imageView, "alpha", 1f, 0f)
            .apply {
                duration = 3000
            }.start()
    }

    private fun rotationY() {
        ObjectAnimator.ofFloat(cartViewBinding.imageView, "rotationY", 0f, 360f)
            .apply {
                duration = 3000
            }.start()
    }

    private fun hideImageViewAnimation() {
        ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 3000
            addUpdateListener {
                val currentValue = it.animatedValue as Float
                cartViewBinding.imageView.alpha = currentValue
                cartViewBinding.imageView.requestLayout()
            }
        }.start()
    }
}