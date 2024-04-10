package com.wpf.app.quickrecyclerview.data

import android.view.View
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quickbind.utils.DataAutoSet2ViewUtils

/**
 * 可以网络请求的数据Item
 */
open class QuickRequestData @JvmOverloads constructor(
    open val autoSet: Boolean = true,
) : QuickSuspensionData(), com.wpf.app.base.bind.Bind {

    @Transient
    private var mView: View? = null

    open fun onCreateView(view: View) {
        mView = view
        QuickBindWrap.bind(this)
        if (autoSet) {
            DataAutoSet2ViewUtils.autoSet(this, view)
        }
    }

    override fun getView(): View? {
        return mView
    }
}