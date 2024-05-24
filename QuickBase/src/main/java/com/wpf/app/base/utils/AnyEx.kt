package com.wpf.app.base.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import com.wpf.app.base.ability.scope.ContextScope

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