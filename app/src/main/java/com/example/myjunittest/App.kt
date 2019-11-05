package com.example.myjunittest

import android.app.Activity
import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * @author rocker
 */
class App : Application(), ViewModelStoreOwner {
    private lateinit var mAppViewModelStore: ViewModelStore
    private lateinit var mFactory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()
        mAppViewModelStore = ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }

    /**
     * 檢查activity application是否存在
     * @param activity
     * @return Application
     */
    fun checkApplication(activity: Activity): Application {
        var application = activity.application
        if (application == null) {
            throw IllegalStateException("Can't not get activity application")
        }
        return application
    }

    /**
     * 取得ViewModelProvider.Factory
     * @param activity
     * @return ViewModelProvider.Factory
     */
    fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
        var application = checkApplication(activity)
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        }
        return mFactory
    }

    /**
     * 取得 ViewModelProvider
     * @param activity
     * @return ViewModelProvider
     */
    fun getAppViewModelProvider(activity: Activity): ViewModelProvider {
        val app = activity.applicationContext as App
        return ViewModelProvider(app, app.getAppFactory(activity))
    }


}