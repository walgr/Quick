package com.wpf.app.quickwork.widget.theme

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.annotation.ColorInt
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.toColor

interface QuickTextThemeBase {
    val textView: TextView

    var curTheme: QuickTextThemeI?

    fun initTextTheme(context: Context, attrs: AttributeSet? = null, theme: QuickTextThemeI? = null) {
        curTheme = theme ?: defaultTheme ?: QuickTextTheme()
        curTheme?.initDataByXml(context)
        setTheme(curTheme!!)
    }

    fun setTheme(style: QuickTextThemeI) {
        textView.apply {
            style.apply {
                background?.let {
                    setBackground(it)
                }
                textColor?.let {
                    setTextColor(it)
                }
                hintTextColor?.let {
                    setHintTextColor(it)
                }
                textSize?.let {
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, it)
                }
                textGravity?.let {
                    gravity = it
                }
                isBold?.let {
                    setBold(it)
                }
                isSingleLine?.let {
                    setSingleLine(it)
                }
                maxLines?.let {
                    setMaxLines(it)
                }
                lines?.let {
                    setLines(it)
                }
                ems?.let {
                    setEms(it)
                }
                maxEms?.let {
                    setMaxEms(it)
                }
                maxWidth?.let {
                    setMaxWidth(it)
                }
                includeFontPadding?.let {
                    setIncludeFontPadding(it)
                }
                ellipsize?.let {
                    setEllipsize(it)
                }
            }
        }
    }

    fun setBold(bold: Boolean = true) {
        textView.apply {
            setTypeface(
                if (bold) Typeface.defaultFromStyle(Typeface.BOLD) else Typeface.defaultFromStyle(
                    Typeface.NORMAL
                )
            )
        }
    }

    companion object {
        internal var defaultTheme: QuickTextThemeI? = null
            get() = field?.copy()

        @Suppress("unused")
        fun registerDefaultTheme(defaultThemeRun: QuickTextThemeI.() -> Unit) {
            val theme = QuickTextTheme()
            defaultThemeRun.invoke(theme)
            defaultTheme = theme
        }
    }
}


interface QuickTextThemeI {
    var background: Drawable?
    var textColor: Int?
    var hintTextColor: Int?
    var textSize: Float?
    var textGravity: Int?
    var isBold: Boolean?
    var isSingleLine: Boolean?
    var maxLines: Int?
    var lines: Int?
    var ems: Int?
    var maxEms: Int?
    var maxWidth: Int?
    var includeFontPadding: Boolean?
    var ellipsize: TextUtils.TruncateAt?

    fun initDataByXml(context: Context) {
        textColor = textColor ?: android.R.color.white.toColor(context)
        hintTextColor = hintTextColor ?: android.R.color.darker_gray.toColor(context)
        textSize = textSize ?: 14f.dp
    }

    fun with(other: QuickTextThemeI?): QuickTextThemeI {
        background = background ?: other?.background
        textColor = textColor ?: other?.textColor
        hintTextColor = hintTextColor ?: other?.hintTextColor
        textSize = textSize ?: other?.textSize
        textGravity = textGravity ?: other?.textGravity
        isBold = isBold ?: other?.isBold
        isSingleLine = isSingleLine ?: other?.isSingleLine
        maxLines = maxLines ?: other?.maxLines
        lines = lines ?: other?.lines
        ems = ems ?: other?.ems
        maxEms = maxEms ?: other?.maxEms
        maxWidth = maxWidth ?: other?.maxWidth
        includeFontPadding = includeFontPadding ?: other?.includeFontPadding
        ellipsize = ellipsize ?: other?.ellipsize
        return this
    }

    fun copy(): QuickTextThemeI {
        return QuickTextTheme(
            background,
            textColor,
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

open class QuickTextTheme(
    override var background: Drawable? = null,
    @ColorInt override var textColor: Int? = null,
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
) : QuickTextThemeI