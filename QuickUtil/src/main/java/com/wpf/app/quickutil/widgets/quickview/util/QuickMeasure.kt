package com.wpf.app.quickutil.widgets.quickview.util

import android.graphics.Canvas

/**
 * Created by 王朋飞 on 2022/9/1.
 *
 */
interface QuickMeasure {

    fun Measure(widthMeasureSpec: Int, heightMeasureSpec: Int)

    fun Layout(changed: Boolean, l: Int, t: Int, r: Int, b: Int)

    fun Draw(canvas: Canvas?)
}