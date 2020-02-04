package com.example.myjunittest.utils

import android.content.Context
import android.content.SharedPreferences

interface ISharePreferenceManager {
    val context: Context
    fun saveString(key: String, value: String)
    fun getString(key: String): String
}

class SharePreferenceManager(override val context: Context) : ISharePreferenceManager {
    private val sharedPreferenceKey = "USER_DATA"
    var sharePreferences: SharedPreferences

    init {
        sharePreferences = context.getSharedPreferences(sharedPreferenceKey, Context.MODE_PRIVATE)
    }


    override fun saveString(
        key: String,
        value: String
    ) {
        sharePreferences.edit().putString(key, value).commit()
    }

    override fun getString(key: String): String {
        return sharePreferences.getString(key,"")!!
    }
}