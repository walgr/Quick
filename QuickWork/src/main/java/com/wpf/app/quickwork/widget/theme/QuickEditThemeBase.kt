package com.wpf.app.quickwork.widget.theme

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import com.wpf.app.quickutil.helper.generic.forceTo

interface QuickEditThemeBase : QuickTextThemeBase {
    val editText: EditText
    override val textView: TextView
        get() = editText
    override var curTheme: QuickTextThemeI?

    fun initEditTheme(
        context: Context,
        attrs: AttributeSet?,
        theme: QuickEditThemeI?,
    ) {
        curTheme = theme ?: defaultTheme ?: QuickEditTheme()
        curTheme?.initDataByXml(context)
        setTheme(curTheme!!.forceTo())
    }

    fun setTheme(style: QuickEditTheme) {
        editText.apply {
            super.setTheme(style)
            hint = style.hint
            background = style.background
        }
    }

    companion object {
        var defaultTheme: QuickEditThemeI? = null
            get() = field?.copy()

        @Suppress("unused")
        fun registerDefaultTheme(defaultThemeRun: QuickEditThemeI.() -> Unit) {
            val theme = QuickEditTheme()
            defaultThemeRun.invoke(theme)
            defaultTheme = theme
        }
    }
}


interface QuickEditThemeI : QuickTextThemeI {
    var hint: String?

    fun with(other: QuickEditThemeI?): QuickEditThemeI {
        super.with(other)
        hint = hint ?: other?.hint
        return this
    }

    override fun copy(): QuickEditThemeI {
        return QuickEditTheme(
            background,
            textColor,
            hint,
            hintTextColor,
            textSize,
            textGravity,
            isBold,
            isSingleLine,
            maxLines,
            lines,
            ems,
            maxEms,
            maxWidth,
            includeFontPadding,
            ellipsize
        )
    }
}

open class QuickEditTheme(
    override var background: Drawable? = null,
    @ColorInt override var textColor: Int? = null,
    override var hint: String? = null,
    @ColorInt override var hintTextColor: Int? = null,
    override var textSize: Float? = null,
    override var textGravity: Int? = null,
    override var isBold: Boolean? = null,
    override var isSingleLine: Boolean? = null,
    override var maxLines: Int? = null,
    override var lines: Int? = null,
    override var ems: Int? = null,
    override var maxEms: Int? = null,
    override var maxWidth: Int? = null,
    override var includeFontPadding: Boolean? = null,
    override var ellipsize: TextUtils.TruncateAt? = null,
) : QuickEditThemeI