package com.wpf.app.quick.helper.tab

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes

/**
 * 挪动位置需要同步修改TabInitProcessor里的包名
 */
open class GroupManager {
    protected var change: ((curPos: Int) -> Unit)? = null

    open fun posChange(curPos: Int) {

    }

    open fun posChange(change: (curPos: Int) -> Unit): GroupManager {
        this.change = change
        return this
    }

    open fun <T : View> findChild(parent: View, @IdRes id: Int): T {
        return parent.findViewById(id)
    }

    interface OnGroupChangeListener {
        fun onChange(view: View)
    }
}