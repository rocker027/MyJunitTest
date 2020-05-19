package com.example.myjunittest.ui.menu.animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.myjunittest.R


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
        setupActionBar()
        return inflater.inflate(R.layout.fragment_ec_paging_animation, container, false)
    }

    private fun setupActionBar() {
        activity?.actionBar.also {
            it?.setDisplayHomeAsUpEnabled(true)
            it?.setHomeButtonEnabled(true)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 新增返回功能
        if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
        }

        return super.onOptionsItemSelected(item)
    }
}
