package com.wpf.app.quick.widgets.shadow

interface ShadowView {
    var key: String

    fun onAttachedToWindow() {
        ShadowViewManager.onAttachedToWindow(this)
    }
    fun onDetachedFromWindow() {
        ShadowViewManager.onDetachedFromWindow(this)
    }
}