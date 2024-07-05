package com.wpf.app.quickutil.helper

import android.graphics.Typeface
import android.widget.TextView

fun TextView.bold(isBold: Boolean = false) {
    setTypeface(
        if (isBold) Typeface.defaultFromStyle(Typeface.BOLD)
        else Typeface.defaultFromStyle(Typeface.NORMAL)
    )
}