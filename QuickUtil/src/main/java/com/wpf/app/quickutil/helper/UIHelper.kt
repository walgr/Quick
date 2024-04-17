package com.wpf.app.quickutil.helper

import android.content.Context
import android.content.res.Resources
import com.wpf.app.quickutil.init.QuickInit

/**
 * Created by 王朋飞 on 2022/7/20.
 *
 */
val Float.dp: Float
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics
    )

val Int.dp: Int
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
    ).toInt()


val Float.sp: Float
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_SP, this, Resources.getSystem().displayMetrics
    )


val Int.sp: Int
    get() = android.util.TypedValue.applyDimension(
        android.util.TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics
    ).toInt()

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

fun Int.sp(context: Context? = QuickInit.getContext()): Int {
    if (context == null) {
        throw RuntimeException("context is null")
    }
    val scale: Float = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Int.spF(context: Context? = QuickInit.getContext()): Float {
    if (context == null) {
        throw RuntimeException("context is null")
    }
    val scale: Float = context.resources.displayMetrics.density
    return this * scale + 0.5f
}

fun Float.sp(context: Context? = QuickInit.getContext()): Int {
    if (context == null) {
        throw RuntimeException("context is null")
    }
    val scale: Float = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Float.spF(context: Context? = QuickInit.getContext()): Float {
    if (context == null) {
        throw RuntimeException("context is null")
    }
    val scale: Float = context.resources.displayMetrics.density
    return this * scale + 0.5f
}