package com.wpf.app.quickutil.ability.helper

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.wpf.app.quickutil.ability.scope.ViewGroupScope
import com.wpf.app.quickutil.helper.wishLayoutParams

fun <T : View> T.gravityInParent(gravity: Int): T {
    if (this.parent is LinearLayout) {
        wishLayoutParams<LinearLayout.LayoutParams>().gravity = gravity
    } else if (this.parent is FrameLayout) {
        wishLayoutParams<FrameLayout.LayoutParams>().gravity = gravity
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