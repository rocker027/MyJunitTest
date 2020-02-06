package com.example.myjunittest.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.myjunittest.R
import com.example.myjunittest.databinding.FragmentDashboardBinding
import com.example.myjunittest.ui.home.HomeRepository
import com.example.myjunittest.ui.home.ProductAPI

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeRepository = HomeRepository(ProductAPI())
        dashboardViewModel = DashboardViewModel(homeRepository)
//            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val dataBinding = DataBindingUtil.setContentView<FragmentDashboardBinding>(
            activity!!,
            R.layout.fragment_dashboard
        )

        dataBinding.dashBoardViewModel = dashboardViewModel
        dataBinding.lifecycleOwner =this
        return dataBinding.root
    }
}