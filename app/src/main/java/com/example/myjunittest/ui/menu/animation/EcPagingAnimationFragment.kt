package com.example.myjunittest.ui.menu.animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myjunittest.R
import com.example.myjunittest.utils.layoutmanager.CircleLayoutManager


class EcPagingAnimationFragment : Fragment() {

    companion object {
        fun newInstance() =
            EcPagingAnimationFragment()
    }

    private lateinit var viewModel: EcPagingAnimationViewModel

    // find view
    private lateinit var viewpager: ViewPager2
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EcPagingAnimationViewModel::class.java)
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_ec_paging_animation, container, false)
        initView(view)
        initLiveData()
        setUpComponents()
        return view
    }

    private fun initLiveData() {
        viewModel.currentPositionLiveData.observe(viewLifecycleOwner, Observer { pos ->
            viewpager.currentItem = pos
            recyclerView.smoothScrollToPosition(pos)
        })

    }

    private fun initView(view: View) {
        viewpager = view.findViewById(R.id.vp_imagepager)
        recyclerView = view.findViewById(R.id.rv_items)
    }

    private fun setUpComponents() {
        // 初始化Viewpager
        val data = viewModel.fakeItems
        viewpager.adapter = TopImagePagerAdapter(data)

        // 初始化RecyclerView
        recyclerView.setHasFixedSize(true)
        val circleLayoutManager = CircleLayoutManager(requireContext())
        recyclerView.layoutManager = circleLayoutManager
        recyclerView.adapter = SwipItemsAdapter(data)

        // recyclerView scroll狀態
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(rv: RecyclerView, newState: Int) {
                super.onScrollStateChanged(rv, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val currentPosition = (rv.layoutManager as CircleLayoutManager).currentPosition
                    viewModel.currentPositionLiveData.value = currentPosition
                }
            }
        })

        // viewpager on page change
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.currentPositionLiveData.value = position
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

