package com.wpf.app.quickutil.helper.generic

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import com.wpf.app.quickutil.ability.scope.ContextScope
import com.wpf.app.quickutil.utils.LogUtil
import java.math.BigDecimal

inline fun <reified T> Any?.asTo(): T? {
    if (this == null) return null
    if (this !is T) return null
    return this
}

inline fun <reified T> Any.forceTo(): T {
    return this as T
}

fun <T: Any> T?.nullDefault(default: T) : T {
    return this ?: default
}

fun Any.printLog(before: String = "", after: String = "", tag: String = com.wpf.app.quickutil.utils.LogUtil.TAG) {
    com.wpf.app.quickutil.utils.LogUtil.e(tag, before + (if (this is Number) BigDecimal(this.toString()) else this) + after)
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
        is ContextScope -> this.context
        else -> null
    }
}