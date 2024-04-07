package com.wpf.app.quickutil.widget

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.wpf.app.quickutil.helper.layoutParams
import com.wpf.app.quickutil.helper.match
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.helper.wrap
import com.wpf.app.quickutil.helper.wrapContentHeightParams
import com.wpf.app.quickutil.helper.wrapContentWidthParams
import com.wpf.app.quickutil.other.forceTo

inline fun <reified T: ViewGroup.LayoutParams> View.wishLayoutParams(width: Int = match, height: Int = wrap): T {
    return this.layoutParams?.forceTo<T>() ?: layoutParams<T>(width, height)
}

fun ViewGroup.smartLayoutParams(layoutParams: ViewGroup.LayoutParams = matchLayoutParams()): ViewGroup.LayoutParams {
    return if (this is LinearLayout) {
        if (this.orientation == LinearLayout.VERTICAL) {
            wrapContentHeightParams()
        } else {
            wrapContentWidthParams()
        }
    } else layoutParams
}