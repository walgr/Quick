package com.wpf.app.quickrecyclerview.data

import android.view.View
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.utils.DataAutoSet2ViewUtils
import com.wpf.app.quickutil.bind.Bind

open class QuickRequestData @JvmOverloads constructor(
    @Transient open val autoSet: Boolean = true,
    @Transient override val isSuspension: Boolean = false                 //View是否悬浮置顶
) : QuickItemData(isSuspension), Bind {

    @Transient
    private var mView: View? = null

    open fun onCreateView(view: View) {
        mView = view
        QuickBind.bind(this)
        if (autoSet) {
            DataAutoSet2ViewUtils.autoSet(this, view)
        }
    }

    override fun getView(): View? {
        return mView
    }
}