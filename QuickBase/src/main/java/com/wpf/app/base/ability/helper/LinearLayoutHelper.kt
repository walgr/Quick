package com.wpf.app.base.ability.helper

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.wpf.app.base.ability.scope.ViewGroupScope
import com.wpf.app.quickutil.widget.wishLayoutParams

fun <T : View> T.gravity(gravity: Int): T {
    if (this is LinearLayout) {
        this.gravity = gravity
        return this
    }
    if (this.parent is LinearLayout) {
        wishLayoutParams<LinearLayout.LayoutParams>().gravity = gravity
    }
    if (this is TextView) {
        this.gravity = gravity
    }
    return this
}

fun <T : ViewGroupScope<LinearLayout>> T.gravity(gravity: Int): T {
    this.view.gravity = gravity
    return this
}

fun <T : LinearLayout> T.orientation(orientation: Int): T {
    this.orientation = orientation
    return this
}

fun <T : ViewGroupScope<LinearLayout>> T.orientation(orientation: Int): T {
    this.view.orientation = orientation
    return this
}