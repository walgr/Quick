package com.wpf.app.quickbind.bindview

import android.view.View
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.utils.DataAutoSet2ViewUtils
import com.wpf.app.quickutil.bind.Bind

open class QuickRequestData(private val autoSet: Boolean = true) : Bind {

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