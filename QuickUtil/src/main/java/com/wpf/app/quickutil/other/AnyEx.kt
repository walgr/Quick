package com.wpf.app.quickutil.other

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.Window
import android.widget.PopupWindow
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import com.wpf.app.quickutil.log.LogUtil
import java.math.BigDecimal

inline fun <reified T> Any?.asTo(): T? {
    if (this == null) return null
    if (this !is T) return null
    return this
}

inline fun <reified T> Any.forceTo(): T {
    return this as T
}

fun Any.context(): Context? {
    return when (this) {
        is Activity -> this
        is Context -> this
        is View -> this.context
        is Fragment -> this.context
        is Dialog -> this.context
        is Window -> this.context
        is PopupWindow -> this.contentView.context
        else -> null
    }
}

fun Any.printLog(before: String = "", after: String = "", tag: String = LogUtil.TAG) {
    LogUtil.e(tag, before + (if (this is Number) BigDecimal(this.toString()) else this) + after)
}