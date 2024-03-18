package com.wpf.app.quickutil.helper

import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.LinearLayout

const val match = ViewGroup.LayoutParams.MATCH_PARENT
const val wrap = ViewGroup.LayoutParams.WRAP_CONTENT

val matchLayoutParams = ViewGroup.LayoutParams(match, match)
val matchWrapLayoutParams = ViewGroup.LayoutParams(match, wrap)
val wrapLayoutParams = ViewGroup.LayoutParams(wrap, wrap)
val wrapMatchLayoutParams = ViewGroup.LayoutParams(wrap, match)

val matchMarginLayoutParams = MarginLayoutParams(match, match)
val matchWrapMarginLayoutParams = MarginLayoutParams(match, wrap)
val wrapMarginLayoutParams = MarginLayoutParams(wrap, wrap)
val wrapMatchMarginLayoutParams = MarginLayoutParams(wrap, match)

val wrapContentHeightParams = LinearLayout.LayoutParams(match, wrap).apply {
    weight = 1f
}

val wrapContentWidthParams = LinearLayout.LayoutParams(wrap, match).apply {
    weight = 1f
}
inline fun <reified T : ViewGroup.LayoutParams> layoutParams(width: Int, height: Int): T {
    return T::class.java.getConstructor(Int::class.java, Int::class.java).newInstance(width, height) as T
}

inline fun <reified T : ViewGroup.LayoutParams> layoutParams(layoutParams: ViewGroup.LayoutParams): T {
    return T::class.java.getConstructor(ViewGroup.LayoutParams::class.java).newInstance(layoutParams) as T
}

fun <T : ViewGroup.LayoutParams> T.with(width: Int = -1, height: Int = -1): T {
    if (width != -1) {
        this.width = width
    }
    if (height != -1) {
        this.height = height
    }
    return this
}