package com.example.myjunittest.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myjunittest.App
import com.example.myjunittest.viewmodel.ShareViewModel


abstract class BaseActivity<VM : ViewModel, VDB : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var mShareViewModel: ShareViewModel
    protected lateinit var mViewModel: VM
    protected lateinit var mDataBinding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutResId = getLayoutResId()
        mDataBinding = DataBindingUtil.setContentView(this, layoutResId)
        mDataBinding.lifecycleOwner = this
        mShareViewModel = getAppViewProvider().get(ShareViewModel::class.java)
        mViewModel = initActivityViewModel()
        initView()
        initData()
    }

    /**
     * 取得Application ViewModelProvider
     */
    private fun getAppViewProvider(): ViewModelProvider {
        val app = applicationContext as App
        return app.getAppViewModelProvider(this)
    }

    /**
     * 初始化 Layout Res Id
     */
    abstract fun getLayoutResId(): Int

    /**
     * 初始化 Activity ViewModel
     */
    abstract fun initActivityViewModel(): VM

    /**
     * 初始化 data
     */
    abstract fun initData()

    /**
     * 初始化 View
     */
    abstract fun initView()
}