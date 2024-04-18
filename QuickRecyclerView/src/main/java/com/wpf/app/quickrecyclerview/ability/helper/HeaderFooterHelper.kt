package com.wpf.app.quickrecyclerview.ability.helper

import android.view.View
import com.wpf.app.quickrecyclerview.QuickRecyclerView
import com.wpf.app.quickrecyclerview.widget.QuickFooterShadow
import com.wpf.app.quickrecyclerview.widget.QuickHeaderShadow

//interface QuickRecyclerViewHeaderScope: ViewScope

fun QuickRecyclerView.header(
    isSuspension: Boolean = false,
    isMatch: Boolean = true,
    builder: QuickHeaderShadow.() -> View
): QuickHeaderShadow {
    val headerView = QuickHeaderShadow(context, isSuspension = isSuspension, isMatch = isMatch)
    headerView.addView(builder.invoke(headerView))
    return headerView
}

fun QuickRecyclerView.footer(
    isMatch: Boolean = true,
    builder: QuickFooterShadow.() -> View
): QuickFooterShadow {
    val footerView = QuickFooterShadow(context, isMatch = isMatch)
    footerView.addView(builder.invoke(footerView))
    return footerView
}