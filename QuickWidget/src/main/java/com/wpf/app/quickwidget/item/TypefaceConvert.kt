package com.wpf.app.quickwidget.item

import android.graphics.Typeface

/**
 * Created by 王朋飞 on 2022/5/9.
 *
 */
object TypefaceConvert {

    fun getAndroidTypeface(style: Int): Typeface {
        return when(style) {
            0 -> Typeface.defaultFromStyle(Typeface.NORMAL)
            1 -> Typeface.defaultFromStyle(Typeface.BOLD)
            2 -> Typeface.defaultFromStyle(Typeface.ITALIC)
            else -> Typeface.defaultFromStyle(Typeface.NORMAL)
        }
    }
}