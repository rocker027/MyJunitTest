package com.example.myjunittest.ui.menu.animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myjunittest.R
import com.example.myjunittest.utils.layoutmanager.CircleLayoutManager


class EcPagingAnimationFragment : Fragment() {

    companion object {
        fun newInstance() =
            EcPagingAnimationFragment()
    }

    private lateinit var viewModel: EcPagingAnimationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EcPagingAnimationViewModel::class.java)
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_ec_paging_animation, container, false)
        setupRecyclerView(view.findViewById(R.id.rv_items))
        return view
    }

    // 初始化RecyclerView
    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        val circleLayoutManager = CircleLayoutManager(context!!)
        recyclerView.layoutManager = circleLayoutManager
        recyclerView.adapter = SwipItemsAdapter(viewModel.getFakeItems())
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

