package com.example.myjunittest.utils

import android.app.Activity
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.View


/**
 * 取得螢幕尺寸的方法
 *
 * @return Pair< width : Int, height : Int> 回傳寬高
 */
fun Activity.getScreenSize(): Pair<Int, Int> {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    val width = displayMetrics.widthPixels
    val height = displayMetrics.heightPixels
    return Pair(width, height)
}

/**
 * 解析度轉換
 */
val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

/**
 * 防止View Double Click
 *
 * 使用方式：
 * button.setSafeOnClickListener {showSettingsScreen()}
 *
 */
fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}