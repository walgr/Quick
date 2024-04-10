package com.wpf.app.quickutil.helper

import android.graphics.drawable.Drawable

fun Drawable.copy(): Drawable? {
    return constantState?.newDrawable()
}