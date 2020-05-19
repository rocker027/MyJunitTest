package com.example.myjunittest

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import com.example.myjunittest.api.ApiConfig
import com.example.myjunittest.api.BaseInterceptor
import com.example.myjunittest.api.ServiceApi
import com.example.myjunittest.db.AppDatabase
import com.example.myjunittest.db.dao.UserDao
import com.example.myjunittest.viewmodel.ShareViewModel
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @author rocker
 */
class App : Application(), ViewModelStoreOwner {
    private lateinit var mAppViewModelStore: ViewModelStore

    override fun onCreate() {
        super.onCreate()
        // init database
        AppDatabase.getInstance(this)

        val intentFilter = IntentFilter(DeepLinkHandler.ACTION)
        LocalBroadcastManager.getInstance(this).registerReceiver(DeepLinkReceiver(), intentFilter)

        mAppViewModelStore = ViewModelStore()

        // koin
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore
    }
}

private val appModule = module {
    // 取得ShareViewModel
    viewModel {
        ViewModelProvider(androidApplication() as App, get()).get(ShareViewModel::class.java)
    }

    // 取得ViewModelProvider.Factory singleton
    single<ViewModelProvider.Factory> {
        ViewModelProvider.AndroidViewModelFactory.getInstance(androidApplication())
    }

    // 取得AppDatabase singleton
    single<AppDatabase> {
        AppDatabase.getInstance(androidApplication())!!
    }

    // 取得UserDoa singleton
    single<UserDao> { get<AppDatabase>().userDao() }

    // api
    factory<BaseInterceptor> {
        BaseInterceptor()
    }

    factory<Retrofit> {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .baseUrl(ApiConfig.baseUrl)
            .build()
    }

    factory<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<BaseInterceptor>())
            .connectTimeout(ApiConfig.TIME_OUT_CONNECT.toLong(), TimeUnit.SECONDS)
            .readTimeout(ApiConfig.TIME_OUT_READ.toLong(), TimeUnit.SECONDS)
            .writeTimeout(ApiConfig.TIME_OUT_WRITE.toLong(), TimeUnit.SECONDS)
            .build()
    }

    single<ServiceApi> {
        get<Retrofit>().create(ServiceApi::class.java)
    }
}

class DeepLinkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val deepLinkUri = intent.getStringExtra(DeepLinkHandler.EXTRA_URI)
        if (intent.getBooleanExtra(DeepLinkHandler.EXTRA_SUCCESSFUL, false)) {
            Log.i(TAG, "Success deep linking: $deepLinkUri")
        } else {
            val errorMessage =
                intent.getStringExtra(DeepLinkHandler.EXTRA_ERROR_MESSAGE)
            Log.e(
                TAG,
                "Error deep linking: $deepLinkUri with error message +$errorMessage"
            )
        }
    }

    companion object {
        private const val TAG = "DeepLinkReceiver"
    }
}