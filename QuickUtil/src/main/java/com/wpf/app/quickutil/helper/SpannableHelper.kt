package com.wpf.app.quickutil.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.annotation.ColorInt

fun String.toSpannableBuilder() = SpannableStringBuilder(this)

fun SpannableStringBuilder.img(
    text: String,
    drawable: Drawable,
): SpannableStringBuilder {
    val start = indexOf(text)
    if (start < 0) return this
    return img(start, start + text.length, drawable)
}

fun SpannableStringBuilder.img(
    startIndex: Int,
    endIndex: Int,
    drawable: Drawable,
): SpannableStringBuilder {
    setSpan(
        ImageSpan(drawable), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE
    )
    return this
}

fun SpannableStringBuilder.img(
    text: String,
    context: Context,
    bitmap: Bitmap,
): SpannableStringBuilder {
    val start = indexOf(text)
    if (start < 0) return this
    return img(start, start + text.length, context, bitmap)
}

fun SpannableStringBuilder.img(
    startIndex: Int,
    endIndex: Int,
    context: Context,
    bitmap: Bitmap,
): SpannableStringBuilder {
    setSpan(
        ImageSpan(context, bitmap), startIndex, endIndex, Spanned.SPAN_INCLUSIVE_EXCLUSIVE
    )
    return this
}

fun SpannableStringBuilder.foregroundColor(
    text: String,
    @ColorInt color: Int,
): SpannableStringBuilder {
    val start = indexOf(text)
    if (start < 0) return this
    setSpan(
        ForegroundColorSpan(color), start, start + text.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE
    )
    return this
}

fun SpannableStringBuilder.bold(
    text: String,
    startIndex: Int = 0,
    isOnce: Boolean = false,
): SpannableStringBuilder {
    var start = -1 + startIndex
    do {
        start = indexOf(text, start + 1)
        if (start >= 0) {
            setSpan(
                StyleSpan(Typeface.BOLD), start, start + text.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
    } while (start != -1 && !isOnce)
    return this
}

fun SpannableStringBuilder.click(
    text: String,
    @ColorInt color: Int,
    startIndex: Int = 0,
    isOnce: Boolean = false,
    addUnderline: Boolean = false,
    clickListener: View.OnClickListener,
): SpannableStringBuilder {
    var start = -1 + startIndex
    do {
        start = indexOf(text, start + 1)
        if (start >= 0) {
            if (addUnderline) {
                setSpan(
                    UnderlineSpan(), start, start + text.length,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            setSpan(
                object : ClickableSpan() {

                    override fun onClick(widget: View) {
                        clickListener.onClick(widget)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        ds.color = color
                    }

                }, start, start + text.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
    } while (start != -1 && !isOnce)
    return this
}

fun SpannableStringBuilder.addUnderline(text: String): SpannableStringBuilder {
    val start = indexOf(text)
    if (start < 0) return this
    setSpan(
        UnderlineSpan(), start, start + text.length,
        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
    )
    return this
}