package com.wpf.app.quickwork.widget.theme

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.wpf.app.quickwidget.title.QuickTitleThemeI
import com.wpf.app.quickwidget.title.QuickTitleView
import com.wpf.app.quickwork.widget.theme.QuickTitleThemeBase

open class QuickThemeTitle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    theme: QuickTitleThemeI? = null,
) : QuickTitleView(context, attrs, defStyleAttr), QuickTitleThemeBase {

    override val titleView: QuickTitleView = this
    override var moreGroupLayout: ViewGroup? = getMoreGroup()
    override var curTheme: QuickTitleThemeI? = null

    init {
        super.initTitleTheme(context, attrs, theme)
    }

    override fun initAttrsInXml(attrs: AttributeSet?) {

    }
}