package com.wpf.app.quickrecyclerview.data

import android.view.View

abstract class QuickStateData(
    open var state: Boolean = false,
    override val layoutId: Int = 0,
    override val layoutView: View? = null,
    override val isSuspension: Boolean = false,
): QuickClickData() {

    override fun onClick() {
        state = !state
        stateChange(state)
    }

    abstract fun stateChange(state: Boolean)
}