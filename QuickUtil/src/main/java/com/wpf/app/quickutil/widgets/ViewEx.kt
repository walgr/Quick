package com.wpf.app.quickutil.widgets

import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import com.wpf.app.quickutil.base.asTo

fun <V> ViewGroup.getChild(isViewGroup: (View) -> Boolean): V? {
    for(it in 0 until this.childCount) {
        val child = this.getChildAt(it)
        if (child is ViewGroup) {
            if (isViewGroup.invoke(child)) {
                return child as V
            } else {
                val childCheck = child.getChild<V>(isViewGroup)
                if (childCheck != null) {
                    return childCheck
                }
            }
        } else {
            if (isViewGroup.invoke(child)) {
                return child as V
            }
        }
    }
    return null
}

fun View.removeParent() {
    this.parent.asTo<ViewGroup>()?.removeView(this)
}

fun <T : View>T.addToParent(parent: ViewParent): T {
    parent.asTo<ViewGroup>()?.addView(this)
    return this
}