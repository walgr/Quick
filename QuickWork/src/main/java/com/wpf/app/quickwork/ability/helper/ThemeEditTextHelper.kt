package com.wpf.app.quickwork.ability.helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.ColorInt
import com.wpf.app.base.ability.helper.addView
import com.wpf.app.base.ability.scope.ContextScope
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.quickutil.helper.wrapMarginLayoutParams
import com.wpf.app.quickwidget.shadow.ShadowEditView
import com.wpf.app.quickwork.widget.theme.QuickEditTheme
import com.wpf.app.quickwork.widget.theme.QuickEditThemeBase
import com.wpf.app.quickwork.widget.theme.QuickEditThemeI
import com.wpf.app.quickwork.widget.theme.QuickTextThemeI
import com.wpf.app.quickwork.widget.theme.QuickThemeEditView

fun EditText.setTheme(
    theme: QuickEditThemeI,
) {
    object : QuickEditThemeBase {
        override val editText: EditText = this@setTheme
        override var curTheme: QuickTextThemeI? = theme
    }.setTheme(theme)
}

fun ViewGroupScope<out ViewGroup>.shadowEdit(
    layoutParams: ViewGroup.LayoutParams = wrapMarginLayoutParams(),
    background: Drawable? = null,
    hint: String? = null,
    theme: QuickEditThemeI? = null,
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
    builder: (ShadowEditView.() -> Unit)? = null,
): ShadowEditView {
    val mContext: Context = context
    val curTheme = (theme ?: QuickEditThemeBase.defaultTheme ?: QuickEditTheme()).apply {
        this.background = background ?: this.background
        this.textColor = textColor ?: this.textColor
        this.hint = hint ?: this.hint
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
    }
    val textView = ShadowEditView(mContext)
    textView.setTheme(curTheme)
    builder?.invoke(textView)
    addView(textView, layoutParams)
    return textView
}


fun ContextScope.edit(
    layoutParams: ViewGroup.LayoutParams = wrapMarginLayoutParams(),
    background: Drawable? = null,
    theme: QuickEditThemeI? = null,
    @ColorInt textColor: Int? = null,
    hint: String? = null,
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
    builder: (QuickThemeEditView.() -> Unit)? = null,
): QuickThemeEditView {
    val mContext: Context = context
    val view = QuickThemeEditView(
        mContext,
        theme = (theme ?: QuickEditThemeBase.defaultTheme ?: QuickEditTheme()).apply {
            this.background = background ?: this.background
            this.textColor = textColor ?: this.textColor
            this.hint = hint ?: this.hint
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
        }
    )
    builder?.invoke(view)
    addView(view, layoutParams)
    return view
}