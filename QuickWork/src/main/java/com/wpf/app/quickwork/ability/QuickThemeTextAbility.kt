package com.wpf.app.quickwork.ability

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import com.wpf.app.quickutil.helper.parent
import com.wpf.app.quickutil.helper.wrapLayoutParams
import com.wpf.app.quickutil.other.context
import com.wpf.app.quickwork.widget.QuickThemeTextView
import com.wpf.app.quickwork.widget.theme.QuickTextTheme
import com.wpf.app.quickwork.widget.theme.QuickTextThemeBase

fun Any.textButton(
    textView: QuickThemeTextView,
    clickListener: OnClickListener? = null,
    init: (TextView.() -> Unit)? = null,
): QuickThemeTextView {
    textView.setOnClickListener(clickListener)
    init?.invoke(textView)
    if (this is ViewGroup) {
        if (textView.parent() == null) {
            this.addView(textView)
        }
    }
    return textView
}

fun Any.text(
    layoutParams: ViewGroup.LayoutParams = wrapLayoutParams(),
    background: Drawable? = null,
    text: String,
    theme: QuickTextTheme? = null,
    @ColorInt textColor: Int? = null,
    @ColorInt hintTextColor: Int? = null,
    textSize: Float? = null,                  //单位px
    ellipsize: TextUtils.TruncateAt? = null,
    isBold: Boolean? = null,
    isSingleLine: Boolean? = null,
    maxLines: Int? = null,
    lines: Int? = null,
    maxEms: Int? = null,
    ems: Int? = null,
    maxWidth: Int? = null,
    includeFontPadding: Boolean? = null,
    textGravity: Int? = null,
    onClick: OnClickListener? = null,
    onLongClick: View.OnLongClickListener? = null,
    builder: (QuickThemeTextView.() -> Unit)? = null
): QuickThemeTextView {
    val mContext: Context = context()!!
    val textView = QuickThemeTextView(mContext, theme = (theme ?: QuickTextThemeBase.commonTheme?: QuickTextTheme()).apply {
        this.background = background ?: this.background
        this.textColor = textColor ?: this.textColor
        this.hintTextColor = hintTextColor ?: this.hintTextColor
        this.textSize = textSize ?: this.textSize
        this.ellipsize = ellipsize ?: this.ellipsize
        this.isBold = isBold ?: this.isBold
        this.isSingleLine = isSingleLine ?: this.isSingleLine
        this.maxLines = maxLines ?: this.maxLines
        this.lines = lines ?: this.lines
        this.maxEms = maxEms ?: this.maxEms
        this.ems = ems ?: this.ems
        this.maxWidth = maxWidth ?: this.maxWidth
        this.includeFontPadding = includeFontPadding ?: this.includeFontPadding
        this.textGravity = textGravity ?: this.textGravity
    })
    textView.text = text
    textView.setOnClickListener(onClick)
    textView.setOnLongClickListener(onLongClick)
    builder?.invoke(textView)
    if (this is ViewGroup) {
        addView(textView, layoutParams)
    }
    return textView
}