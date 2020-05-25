package com.example.myjunittest.ui.menu.animation

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myjunittest.MainActivity
import com.example.myjunittest.R
import com.example.myjunittest.api.GankData
import com.example.myjunittest.base.BaseFragment
import com.example.myjunittest.databinding.FragmentEcPagingAnimationBinding
import com.example.myjunittest.utils.layoutmanager.CircleLayoutManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.coroutines.launch


class EcPagingAnimationFragment : BaseFragment() {
    private lateinit var viewModel: EcPagingAnimationViewModel
    private lateinit var viewpager: ViewPager2
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTitle: TextView
    private lateinit var tvUpdateTime: TextView
    private lateinit var tvDesc: TextView
    private lateinit var swipItemsAdapter: SwipItemsAdapter
    private lateinit var topImagePagerAdapter: TopImagePagerAdapter
    private lateinit var btnCart: MaterialButton
    private lateinit var viewBinding: FragmentEcPagingAnimationBinding

    private val animationFade = ValueAnimator.ofFloat(0.0f, 1.0f).apply {
        duration = 1000
        addUpdateListener { animation ->
//            tvTitle.alpha = animation.animatedValue as Float
//            tvTitle.requestLayout()
            tvDesc.alpha = animation.animatedValue as Float
            tvDesc.requestLayout()
            tvUpdateTime.alpha = animation.animatedValue as Float
            tvUpdateTime.requestLayout()
        }
    }

    val frameHolder = PropertyValuesHolder.ofKeyframe(
        "rotation",
        Keyframe.ofFloat(0f, 0f),
        Keyframe.ofFloat(0.1f, -30f),
        Keyframe.ofFloat(0.3f, 30f),
        Keyframe.ofFloat(0.6f, -30f),
        Keyframe.ofFloat(0.8f, 30f),
        Keyframe.ofFloat(1f, 0f)
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EcPagingAnimationViewModel::class.java)
        viewBinding = FragmentEcPagingAnimationBinding.inflate(inflater)
        setHasOptionsMenu(true)
        (activity as MainActivity).hideToolBar()
        fetchGankApiData()
        initView()
        initLiveData()
        setUpComponents()
        return viewBinding.root
    }

    private fun fetchGankApiData() {
        viewModel.callGankApiData()
    }

    sealed class UiStatus {
        /**
         * 更新當前item位置
         *
         * @property pos index
         */
        data class UpdateCurrentPosititon(val pos: Int) : UiStatus()

        /**
         * call api完成更新adapter
         */
        object UpdateGankData : UiStatus()
    }

    /**
     * 初始化 live data
     */
    private fun initLiveData() {
        viewModel.fragmentLiveData.observe(
            viewLifecycleOwner,
            Observer { uiState ->
                val newData = viewModel.gankDatas
                when (uiState) {
                    is UiStatus.UpdateCurrentPosititon -> {
                        viewpager.currentItem = uiState.pos
                        recyclerView.smoothScrollToPosition(uiState.pos)

                        newData[uiState.pos].run {
                            val animator =
                                ObjectAnimator.ofPropertyValuesHolder(tvTitle, frameHolder).apply {
                                    duration = 1000
                                }
                            animator.start()
                            animationFade.start()

                            tvTitle.text = title
                            tvDesc.text = desc
                            tvUpdateTime.text = createdAt
                        }
                    }
                    is UiStatus.UpdateGankData -> {
                        swipItemsAdapter.setNewData(newData)
                        topImagePagerAdapter.setNewData(newData)
                    }
                }
            })

    }

    /**
     * 初始化 View Components
     */
    private fun initView() {
        viewpager = viewBinding.vpImagepager
        recyclerView = viewBinding.rvItems
        tvTitle = viewBinding.tvTitle
        tvUpdateTime = viewBinding.tvUpdateTime
        tvDesc = viewBinding.tvDesc
        btnCart = viewBinding.btnCart

        btnCart.setOnClickListener {
            // 設定要顯示的共享元素 id
            val extras = FragmentNavigatorExtras(
                btnCart to "root_bg"
            )
            // 將共享元素資訊加到NavController
            findNavController().navigate(
                R.id.action_ecPagingAnimationFragment_to_ecPagingAnimationCartFragment,
                null,
                null,
                extras
            )
        }
    }

    private fun setUpComponents() {
        // 初始化Viewpager
        val data = mutableListOf<GankData>()
        topImagePagerAdapter = TopImagePagerAdapter(data)
        viewpager.adapter = topImagePagerAdapter

        // 初始化RecyclerView
        recyclerView.setHasFixedSize(true)
        val circleLayoutManager = CircleLayoutManager(requireContext())
        recyclerView.layoutManager = circleLayoutManager
        swipItemsAdapter = SwipItemsAdapter(data)
        recyclerView.adapter = swipItemsAdapter
        // 讓recyclerview 有 pager效果
        PagerSnapHelper().attachToRecyclerView(recyclerView)

        // recyclerView scroll狀態
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(rv: RecyclerView, newState: Int) {
                super.onScrollStateChanged(rv, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val currentPosition = (rv.layoutManager as CircleLayoutManager).currentPosition
                    mainScope.launch {
                        viewModel.fragmentLiveData.value =
                            UiStatus.UpdateCurrentPosititon(currentPosition)
                    }
                }
            }
        })

        // viewpager on page change
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mainScope.launch {
                    viewModel.fragmentLiveData.value =
                        UiStatus.UpdateCurrentPosititon(position)
                }
            }
        })
    }


    // 捕捉toolbar item click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 新增返回功能
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }
}
