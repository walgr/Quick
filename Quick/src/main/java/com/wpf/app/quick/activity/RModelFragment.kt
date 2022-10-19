package com.wpf.app.quick.activity

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.wpf.app.quick.R
import com.wpf.app.quick.viewmodel.QuickViewModel
import com.wpf.app.quickrecyclerview.QuickRecyclerView

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class RModelFragment<VM : QuickViewModel<H>, H : QuickView> @JvmOverloads constructor(
    @LayoutRes override val layoutId: Int = 0,
    @IdRes open val listId: Int = 0,
    override val titleName: String = ""
) : QuickViewModelFragment<VM, H>(layoutId = layoutId) {
    private var mRecyclerView: com.wpf.app.quickrecyclerview.QuickRecyclerView? = null

    @CallSuper
    override fun initView(view: View?) {
        if (view != null) {
            mRecyclerView = view.findViewById(if (listId == 0) R.id.recyclerView else listId)
        }
        super.initView(view)
    }

    open fun getRecyclerView(): com.wpf.app.quickrecyclerview.QuickRecyclerView? {
        return mRecyclerView
    }
}