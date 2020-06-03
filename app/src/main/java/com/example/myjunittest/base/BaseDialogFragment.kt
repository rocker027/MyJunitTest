package com.example.myjunittest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.ParameterizedType


abstract class BaseDialogFragment<VM : ViewModel, VB : ViewBinding> : DialogFragment() {
    protected lateinit var dialogViewBinding: VB
    protected lateinit var dialogViewModel: VM

    override fun onStart() {
        super.onStart()
        // 在onStart設定Dialog寬高
        setupDialogWindow()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewBinding()
        initViewModel()
        setupDialogSettings()
        initLiveData()
        initView()
        initData()
        return dialogViewBinding.root
    }

    // 初始化ViewBinding
    private fun initViewBinding() {
        //利用反射，呼叫指定ViewBinding中的inflate方法填充檢視
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[1] as Class<VB>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        dialogViewBinding = method.invoke(null, layoutInflater, container, false) as VB
    }

    // 初始化ViewModel
    private fun initViewModel() {
        val type = javaClass.genericSuperclass
        val vmClass = if (type is ParameterizedType) {
            type.actualTypeArguments[0] as Class<VM>
        } else {
            //如果没有指定泛型参数，则默认使用BaseViewModel
            BaseViewModel::class.java as Class<VM>
        }

        dialogViewModel = ViewModelProvider(this).get(vmClass)
    }

    /**
     * 初始化設定 Dialog
     *
    dialog?.let {
    it.requestWindowFeature(Window.FEATURE_NO_TITLE)
    it.setCanceledOnTouchOutside(false)
    }
     */
    abstract fun setupDialogSettings()

    /**
     * 初始化 Dialog windows
     */
    abstract fun setupDialogWindow()

    /**
     * 初始化 Dialog View
     */
    abstract fun initView()

    /**
     * 初始化 Dialog Data
     */
    abstract fun initData()

    /**
     * 初始化 LiveData
     */
    abstract fun initLiveData()

}

