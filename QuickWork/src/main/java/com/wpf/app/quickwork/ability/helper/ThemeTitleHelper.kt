package com.wpf.app.quickwork.ability.helper

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.wpf.app.base.NO_SET
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.base.ability.scope.createViewGroupScope
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickwidget.group.QuickSpaceLinearLayout
import com.wpf.app.quickwidget.title.QuickTitleAttrs
import com.wpf.app.quickwidget.title.QuickTitleThemeI
import com.wpf.app.quickwork.widget.QuickThemeTitle
import com.wpf.app.quickwork.widget.theme.QuickTitleThemeBase

fun QuickThemeTitle.moreGroup(
    newSpace: Int = 0,
    builder: ViewGroupScope<QuickSpaceLinearLayout>.() -> Unit,
): QuickSpaceLinearLayout {
    val moreGroup = getMoreGroup()!!
    if (newSpace != moreGroup.getCurrentSpace()) {
        moreGroup.setNewItemSpace(newSpace)
    }
    builder(createViewGroupScope(moreGroup))
    return moreGroup
}

fun ViewGroupScope<out ViewGroup>.title(
    titleName: String = "",
    height: Int? = null,
    layoutParams: LayoutParams = matchWrapLayoutParams().reset(height = height ?: NO_SET),
    theme: QuickTitleThemeI? = null,
    builder: (QuickThemeTitle.() -> Unit)? = null,
): QuickThemeTitle {
    val titleView = QuickThemeTitle(
        context,
        theme = (theme ?: QuickTitleThemeBase.defaultTheme ?: QuickTitleAttrs()).apply {
            this.height = height ?: this.height
        })
    titleView.id = com.wpf.app.quickwidget.R.id.quickTitleView
    titleView.setTitle(titleName)
    builder?.invoke(titleView)
    addView(titleView, layoutParams)
    return titleView
}