package com.wpf.app.quickwork.ability

import android.view.ViewGroup
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickwork.R
import com.wpf.app.quickwork.widget.QuickTitleView

fun ViewGroup.title(
    titleName: String = "",
    showTitle: Boolean = true,
    layoutParams: ViewGroup.LayoutParams = matchWrapLayoutParams,
    builder: (QuickTitleView.() -> Unit)? = null
) {
    if (!showTitle) return
    val titleView = QuickTitleView(context)
    titleView.id = R.id.quickTitleView
    titleView.setTitle(titleName)
    addView(titleView, layoutParams)
    builder?.invoke(titleView)
}