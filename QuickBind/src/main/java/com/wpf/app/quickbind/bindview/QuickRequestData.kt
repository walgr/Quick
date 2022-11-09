package com.wpf.app.quickbind.bindview

import android.view.View
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickutil.bind.Bind

open class QuickRequestData @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    @Transient open val layoutView: View? = null
) : Bind {

    @Transient
    private var mView: View? = null

    open fun onCreateView(view: View) {
        mView = view
        QuickBind.bind(this)
    }

    override fun getView(): View? {
        return mView
    }

}