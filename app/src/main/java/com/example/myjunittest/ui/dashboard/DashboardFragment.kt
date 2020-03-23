package com.example.myjunittest.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myjunittest.R
import com.example.myjunittest.api.ServiceApi
import com.example.myjunittest.ui.home.HomeRepository
import com.example.myjunittest.ui.home.ProductAPI
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    val serviceAPI by inject<ServiceApi>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val homeRepository = HomeRepository(ProductAPI())
        dashboardViewModel = DashboardViewModel(homeRepository)
        serviceAPI.getProduct()
            .subscribeOn(Schedulers.io())
            .subscribe({ t ->  println(t.body()) })
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }
}