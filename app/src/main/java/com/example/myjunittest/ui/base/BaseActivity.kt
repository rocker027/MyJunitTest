package com.example.myjunittest.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.myjunittest.App
import com.example.myjunittest.viewmodel.BaseViewModel
import com.example.myjunittest.viewmodel.ShareViewModel
import java.lang.reflect.ParameterizedType


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
        initActivityViewModel()
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
    @LayoutRes
    abstract fun getLayoutResId(): Int

    /**
     * 初始化 Activity ViewModel
     */
    fun initActivityViewModel() {
        val vmClass: Class<VM> = getViewModelClass(javaClass)
        val app = applicationContext as App
        val appFactory = app.getAppFactory(this)

        mViewModel = ViewModelProvider(this, appFactory).get(vmClass)
    }

    /**
     * 透過Java反射找到對應的VM 指定的ViewModel
     */
    private fun getViewModelClass(aClass: Class<BaseActivity<VM, VDB>>): Class<VM> {
        val type = aClass.genericSuperclass
        return if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<VM>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            BaseViewModel::class.java as Class<VM>
        }
    }

    /**
     * 初始化 data
     */
    abstract fun initData()

    /**
     * 初始化 View
     */
    abstract fun initView()
}