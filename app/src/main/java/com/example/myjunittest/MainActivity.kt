package com.example.myjunittest

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.myjunittest.databinding.ActivityMainBinding
import com.example.myjunittest.ui.base.BaseActivity
import com.example.myjunittest.viewmodel.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

//    override fun initActivityViewModel(): ViewModel =  mShareViewModel

    override fun initData() {
    }

    override fun initView() {
        // 將自定義的Toolbar加進去ActionBar
        setSupportActionBar(activityViewBinding.toolbar)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
//         Passing each menu ID as a set of Ids because each
//         menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration.Builder(
//            navController.graph
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications ,R.id.navigation_menu
            )
        ).build()
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        navView.setupWithNavController(navController)
    }

    fun hideToolBar() {
        val toolbar : Toolbar = findViewById(R.id.toolbar)
        toolbar.visibility = View.GONE
    }
}
