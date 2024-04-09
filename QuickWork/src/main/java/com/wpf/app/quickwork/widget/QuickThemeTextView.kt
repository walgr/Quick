package com.wpf.app.quickwork.widget

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import com.wpf.app.quickutil.helper.attribute.AutoGetAttributeHelper
import com.wpf.app.quickutil.helper.dpF
import com.wpf.app.quickutil.helper.toColor
import com.wpf.app.quickwork.R

class QuickThemeTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
    theme: QuickTextTheme? = null
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var curTheme: QuickTextTheme? = null

    init {
        curTheme = AutoGetAttributeHelper.init(
            context, attrs, R.styleable.QuickThemeTextView, theme ?: commonTheme
        )
        curTheme?.initDataByXml(context)
        setTextStyle(curTheme!!)
    }

    fun setTextStyle(style: QuickTextTheme) {
        style.apply {
            background?.let {
                setBackgroundDrawable(it)
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

    fun setBold(bold: Boolean) {
        setTypeface(
            if (bold) Typeface.defaultFromStyle(Typeface.BOLD) else Typeface.defaultFromStyle(
                Typeface.NORMAL
            )
        )
    }

    companion object {
        var commonTheme: QuickTextTheme? = null

        fun setCommonTheme(context: Context, builder: QuickTextTheme.(context: Context) -> Unit) {
            commonTheme = QuickTextTheme()
            builder.invoke(commonTheme!!, context)
        }
    }

    data class QuickTextTheme(
        var background: Drawable? = null,
        @ColorInt var textColor: Int? = null,
        @ColorInt var hintTextColor: Int? = null,
        var textSize: Float? = null,
        var textGravity: Int? = null,
        var isBold: Boolean? = null,
        var isSingleLine: Boolean? = null,
        var maxLines: Int? = null,
        var lines: Int? = null,
        var ems: Int? = null,
        var maxEms: Int? = null,
        var maxWidth: Int? = null,
        var includeFontPadding: Boolean? = null,
        var ellipsize: TextUtils.TruncateAt? = null
    ) {
        fun initDataByXml(context: Context) {
            if (textColor == null) {
                textColor = android.R.color.white.toColor(context)
            }
            if (hintTextColor == null) {
                hintTextColor = android.R.color.darker_gray.toColor(context)
            }
            if (textSize == null) {
                textSize = 14.dpF()
            }
        }
    }
}