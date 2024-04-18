package com.wpf.app.quick.ability.helper

import android.view.View
import android.widget.LinearLayout
import com.wpf.app.quickutil.widget.wishLayoutParams

fun <T : View> T.gravity(gravity: Int): T {
    if (this is LinearLayout) {
        this.gravity = gravity
        return this
    }
    if (this.parent is LinearLayout) {
        wishLayoutParams<LinearLayout.LayoutParams>().gravity = gravity
    }
    return this
}