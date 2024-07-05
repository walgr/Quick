package com.wpf.app.quickwork.ability.helper

import android.view.ViewGroup.LayoutParams
import com.wpf.app.quickutil.ability.helper.addView
import com.wpf.app.quickutil.ability.scope.ContextScope
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickwidget.group.QuickSpaceLinearLayout
import com.wpf.app.quickwidget.title.QuickTitleAttrs
import com.wpf.app.quickwidget.title.QuickTitleThemeI
import com.wpf.app.quickwork.widget.theme.QuickThemeTitle
import com.wpf.app.quickwork.widget.theme.QuickTitleThemeBase

fun QuickThemeTitle.moreGroup(
    newSpace: Int = 0,
    builder: QuickSpaceLinearLayout.() -> Unit,
): QuickSpaceLinearLayout {
    val moreGroup = getMoreGroup()!!
    if (newSpace != moreGroup.getCurrentSpace()) {
        moreGroup.setNewItemSpace(newSpace)
    }
    builder(moreGroup)
    return moreGroup
}

fun ContextScope.title(
    titleName: String = "",
    height: Int? = null,
    layoutParams: LayoutParams = matchWrapMarginLayoutParams().reset(height = height),
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