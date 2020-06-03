package com.example.myjunittest.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VM : ViewModel, VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var activityViewBinding: VB
    protected lateinit var activityViewModel: VM

    // coroutines
    private val job = SupervisorJob()
    internal val mainScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initViewModel()
        initView()
        initData()
    }


    // 初始化ViewBinding
    fun initViewBinding() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val clazz = type.actualTypeArguments[1] as Class<VB>
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            activityViewBinding = method.invoke(null, layoutInflater) as VB
            setContentView(activityViewBinding.root)
        }
    }

    // 初始化ViewModel
    fun initViewModel() {
        val type = javaClass.genericSuperclass
        val vmClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<VM>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            BaseViewModel::class.java as Class<VM>
        }

        activityViewModel = ViewModelProvider(this).get(vmClass)
    }

    /**
     * 初始化 data
     */
    abstract fun initData()

    /**
     * 初始化 View
     */
    abstract fun initView()

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}