package com.wpf.app.quickwidget.title.ability

import android.view.View
import android.view.ViewGroup
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickwidget.R
import com.wpf.app.quickwidget.title.QuickTitleView

fun QuickTitleView.backClick(click: View.() -> Unit) {
    initClick(onBackClick = click)
}

fun QuickTitleView.titleClick(click: View.() -> Unit) {
    initClick(onTitleClick = click)
}

fun QuickTitleView.subTitleClick(click: View.() -> Unit) {
    initClick(onSubTitleClick = click)
}

fun QuickTitleView.initClick(
    onBackClick: (View.() -> Unit)? = null,
    onTitleClick: (View.() -> Unit)? = null,
    onSubTitleClick: (View.() -> Unit)? = null,
) {
    clickListener = object : QuickTitleView.CommonClickListener {
        override fun onBackClick(view: View) {
            super.onBackClick(view)
            onBackClick?.invoke(view)
        }

        override fun onTitleClick(view: View) {
            super.onTitleClick(view)
            onTitleClick?.invoke(view)
        }

        override fun onSubTitleClick(view: View) {
            super.onSubTitleClick(view)
            onSubTitleClick?.invoke(view)
        }
    }
}

fun ViewGroupScope<out ViewGroup>.title(
    titleName: String = "",
    showTitle: Boolean = true,
    layoutParams: ViewGroup.LayoutParams = matchWrapLayoutParams(),
    builder: (QuickTitleView.() -> Unit)? = null,
): QuickTitleView {
    val titleView = QuickTitleView(context)
    if (!showTitle) return titleView
    titleView.id = R.id.quickTitleView
    titleView.setTitle(titleName)
    addView(titleView, layoutParams)
    builder?.invoke(titleView)
    return titleView
}