package com.wpf.app.quickrecyclerview.data

import android.view.View
import com.wpf.app.quickbind.interfaces.RunOnContext
import java.io.Serializable

abstract class QuickStateData @JvmOverloads constructor(
    @Transient open var state: Boolean = false,
    @Transient override val layoutId: Int = 0,
    @Transient override val layoutView: View? = null,
    @Transient override val layoutViewInContext: RunOnContext<View>? = null,
    @Transient override var viewType: Int = 0,
    @Transient override val isSuspension: Boolean = false,
): QuickClickData(layoutId, layoutView, layoutViewInContext, viewType, isSuspension), Serializable {

    override fun onClick() {
        state = !state
        stateChange(state)
    }

    abstract fun stateChange(state: Boolean)
}