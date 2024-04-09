package com.wpf.app.quickwork.ability

import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.wpf.app.quickutil.helper.matchWrapLayoutParams
import com.wpf.app.quickutil.helper.onceClick
import com.wpf.app.quickwork.widget.QuickThemeTitle

fun QuickThemeTitle.textButton(
    text: CharSequence,
    clickListener: OnClickListener? = null,
    init: (TextView.() -> Unit)? = null,
): TextView {
    val textView = TextView(context).apply {
        this.text = text
        dealChildViewCommonStyle(this)
        init?.invoke(this)
        clickListener?.let {
            onceClick(onClickListener = it)
        }
    }
    addView(textView)
    return textView
}

fun QuickThemeTitle.imgButton(
    @DrawableRes img: Int,
    clickListener: OnClickListener? = null,
    init: (ImageView.() -> Unit)? = null,
) {
    addView(ImageView(context).apply {
        setImageResource(img)
        dealChildViewCommonStyle(this)
        init?.invoke(this)
        clickListener?.let {
            onceClick(onClickListener = it)
        }
    })
}

fun ViewGroup.title(
    titleName: String = "",
    showTitle: Boolean = true,
    layoutParams: ViewGroup.LayoutParams = matchWrapLayoutParams(),
    theme: QuickThemeTitle.QuickTitleTheme? = null,
    builder: (QuickThemeTitle.() -> Unit)? = null,
): QuickThemeTitle {
    val titleView = QuickThemeTitle(context, theme = theme ?: QuickThemeTitle.commonTheme?.copy())
    if (!showTitle) return titleView
    titleView.id = com.wpf.app.quickwidget.R.id.quickTitleView
    theme?.let {
        titleView.setTheme(it)
    }
    titleView.setTitle(titleName)
    addView(titleView, layoutParams)
    builder?.invoke(titleView)
    return titleView
}