package com.example.myjunittest.base

import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseFragment : Fragment() {
    private val job = SupervisorJob()
    internal val mainScope = CoroutineScope(Dispatchers.Main + job)
}