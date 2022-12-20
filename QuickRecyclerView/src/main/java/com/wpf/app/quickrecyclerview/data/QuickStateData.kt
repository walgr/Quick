package com.wpf.app.quickrecyclerview.data

import android.view.View
import com.wpf.app.quickbind.interfaces.RunOnContext

abstract class QuickStateData(
    open var state: Boolean = false,
    override val layoutId: Int = 0,
    @Transient override val layoutView: View? = null,
    @Transient override val layoutViewInContext: RunOnContext<View>? = null,
    override val isSuspension: Boolean = false,
): QuickClickData() {

    override fun onClick() {
        state = !state
        stateChange(state)
    }

    abstract fun stateChange(state: Boolean)
}