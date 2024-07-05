package com.wpf.app.quickutil.helper

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.view.get
import com.wpf.app.quickutil.helper.generic.forceTo

/**
 * Created by 王朋飞 on 2022/6/15.
 */
fun Activity.contentView(): View {
    return findViewById(android.R.id.content)
}

fun Window.contentView(): View? {
    return this.decorView.findViewById(android.R.id.content)
}

fun Activity.myContentView(): View {
    return findViewById<ViewGroup>(android.R.id.content)[0]
}

fun View.activity(): Activity = context as Activity

inline fun <reified T : Activity> View.activityForce(): T = context.forceTo<T>()