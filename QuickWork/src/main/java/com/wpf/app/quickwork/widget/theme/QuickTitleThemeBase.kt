package com.wpf.app.quickwork.widget.theme

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickwidget.R
import com.wpf.app.quickwidget.title.QuickTitleAttrs
import com.wpf.app.quickwidget.title.QuickTitleThemeI
import com.wpf.app.quickwidget.title.QuickTitleView

interface QuickTitleThemeBase {
    val titleView: QuickTitleView
    val moreGroupLayout: ViewGroup?
    var curTheme: QuickTitleThemeI?

    fun initTheme(context: Context, attrs: AttributeSet?, theme: QuickTitleThemeI?) {
        curTheme = AutoGetAttributeHelper.init(
            context, attrs, R.styleable.QuickTitleView, (theme ?: commonTheme ?: QuickTitleAttrs())
        )
        curTheme?.initDataInXml(context)
        this.setTheme(curTheme!!)
        if (titleView.background == null) {
            this.curTheme!!.background?.let {
                titleView.background = it
            }
        }
    }

    fun setTheme(style: QuickTitleThemeI) {
        titleView.setAttrsToView(style.forceTo())
    }

    companion object {
        var commonTheme: QuickTitleThemeI? = null
            get() = field?.copy()
    }
}