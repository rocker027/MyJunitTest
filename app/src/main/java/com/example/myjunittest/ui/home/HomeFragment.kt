package com.example.myjunittest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myjunittest.R

class HomeFragment : Fragment(), HomeContract.IHomeView {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.tv)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //            textView.text = it
        })

        val homeRepository = HomeRepository(ProductAPI())
        HomePresenter(this, homeRepository)

        return root
    }

    override fun onGetResult(productResponse: ProductResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBuySuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBuyFail() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}