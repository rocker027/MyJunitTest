package com.example.myjunittest.ui.menu.animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myjunittest.R
import com.example.myjunittest.api.GankData
import com.example.myjunittest.base.BaseFragment
import com.example.myjunittest.utils.layoutmanager.CircleLayoutManager
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EcPagingAnimationViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_ec_paging_animation, container, false)
        setHasOptionsMenu(true)
        fetchGankApiData()
        initView(view)
        initLiveData()
        setUpComponents()
        return view
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
     *
     * @param view
     */
    private fun initView(view: View) {
        viewpager = view.findViewById(R.id.vp_imagepager)
        recyclerView = view.findViewById(R.id.rv_items)
        tvTitle = view.findViewById(R.id.tv_title)
        tvUpdateTime = view.findViewById(R.id.tv_update_time)
        tvDesc = view.findViewById(R.id.tv_desc)
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
