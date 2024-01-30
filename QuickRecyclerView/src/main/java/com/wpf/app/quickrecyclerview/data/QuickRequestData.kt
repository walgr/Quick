package com.wpf.app.quickrecyclerview.data

import android.view.View
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.utils.DataAutoSet2ViewUtils
import com.wpf.app.quickutil.bind.Bind

/**
 * 可以网络请求的数据Item
 */
open class QuickRequestData @JvmOverloads constructor(
    open val autoSet: Boolean = true,
) : QuickSuspensionData(), Bind {

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