package com.wpf.app.quickutil.helper

import android.content.Context

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */

fun Int.dp2px(context: Context?): Int {
    if (context == null) return 0
    val scale: Float = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Float.dp2px(context: Context?): Int {
    if (context == null) return 0
    val scale: Float = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}