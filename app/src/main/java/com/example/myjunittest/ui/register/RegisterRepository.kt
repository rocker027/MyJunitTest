package com.example.myjunittest.ui.register

import android.content.Context
import com.example.myjunittest.utils.SharePreferenceManager

class RegisterRepository (private val sharePreferenceManager: SharePreferenceManager){
    fun saveUserId(id: String) {
        sharePreferenceManager.saveString("USER_ID",id)
    }
}