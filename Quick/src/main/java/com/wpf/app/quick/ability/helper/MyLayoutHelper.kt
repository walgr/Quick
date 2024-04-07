package com.wpf.app.quick.ability.helper

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.run.RunOnContext
import com.wpf.app.quickutil.widget.smartLayoutParams

fun ViewGroup.myLayout(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    layoutParams: ViewGroup.LayoutParams = smartLayoutParams(),
    builder: (ViewGroup.() -> Unit)? = null,
) {
    addView(InitViewHelper.init(context, layoutId, layoutView, layoutViewInContext), layoutParams)
    builder?.invoke(this)
}