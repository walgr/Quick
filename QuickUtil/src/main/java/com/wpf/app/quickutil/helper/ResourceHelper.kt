package com.wpf.app.quickutil.helper

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.wpf.app.quickutil.init.QuickInit

/**
 * Created by 王朋飞 on 2022/8/4.
 *
 */

fun @receiver:DrawableRes Int.toDrawable(context: Context? = QuickInit.getContext()): Drawable {
    if (context == null) {
        throw RuntimeException("context is null")
    }
    return ContextCompat.getDrawable(context, this)!!
}

fun @receiver:ColorRes Int.toColor(context: Context? = QuickInit.getContext()): Int {
    if (context == null) {
        throw RuntimeException("context is null")
    }
    return ContextCompat.getColor(context, this)
}

fun @receiver:ColorInt Int.alpha(alpha: Float): Int {
    return Color.argb((alpha * 256).toInt(), Color.red(this), Color.green(this), Color.blue(this))
}

fun @receiver:AnimRes Int.toAnim(context: Context? = QuickInit.getContext()): Animation {
    if (context == null) {
        throw RuntimeException("context is null")
    }
    return AnimationUtils.loadAnimation(context, this)
}

fun @receiver:LayoutRes Int.toView(
    context: Context,
    mParent: ViewGroup? = null,
    attachToRoot: Boolean = false
): View = LayoutInflater.from(context).inflate(this, mParent, attachToRoot)