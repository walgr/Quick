package com.wpf.app.quickutil.helper

import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams

val matchLayoutParams =
    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
val matchWrapLayoutParams =
    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
val wrapLayoutParams =
    ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

val matchMarginLayoutParams =
    MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
val matchWrapMarginLayoutParams =
    MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
val wrapMarginLayoutParams =
    MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

fun <T : ViewGroup.LayoutParams> T.with(width: Int = -1, height: Int = -1): T {
    if (width != -1) {
        this.width = width
    }
    if (height != -1) {
        this.height = height
    }
    return this
}