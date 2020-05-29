package com.example.myjunittest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VM : ViewModel, VB : ViewBinding> : Fragment() {
    protected var fragmentViewBinding: VB? = null
    protected lateinit var fragmentViewModel: VM

    // coroutines
    private val job = SupervisorJob()
    internal val mainScope = CoroutineScope(Dispatchers.Main + job)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewBinding()
        initViewModel()
        initView()
        initData()
        return fragmentViewBinding!!.root
    }

    // 初始化ViewBinding
    fun initViewBinding() {
        //利用反射，呼叫指定ViewBinding中的inflate方法填充檢視
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[1] as Class<VB>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        fragmentViewBinding = method.invoke(null, layoutInflater, container, false) as VB
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

        fragmentViewModel = ViewModelProvider(this).get(vmClass)
    }

    abstract fun initData()

    abstract fun initView()


    override fun onDestroyView() {
        fragmentViewBinding = null
        super.onDestroyView()
        job.cancel()
    }
}