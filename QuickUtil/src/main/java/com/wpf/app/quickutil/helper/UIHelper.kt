package com.wpf.app.quickutil.helper

import android.content.Context
import com.wpf.app.quickutil.init.QuickInit

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */

fun Int.dp(context: Context? = QuickInit.getContext()): Int {
    if (context == null) {
        throw RuntimeException("context is null")
    }
    val scale: Float = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Int.dpF(context: Context? = QuickInit.getContext()): Float {
    if (context == null) {
        throw RuntimeException("context is null")
    }
    val scale: Float = context.resources.displayMetrics.density
    return this * scale + 0.5f
}

fun Float.dp(context: Context? = QuickInit.getContext()): Int {
    if (context == null) {
        throw RuntimeException("context is null")
    }
    val scale: Float = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Float.dpF(context: Context? = QuickInit.getContext()): Float {
    if (context == null) {
        throw RuntimeException("context is null")
    }
    val scale: Float = context.resources.displayMetrics.density
    return this * scale + 0.5f
}