package com.wpf.app.quick.activity

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import com.wpf.app.quick.R
import com.wpf.app.quick.activity.viewmodel.QuickViewModel
import com.wpf.app.quickrecyclerview.QuickRecyclerView

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class RModelFragment<VM : QuickViewModel<out QuickView>> @JvmOverloads constructor(
    @LayoutRes layoutId: Int = 0,
    @IdRes open val listId: Int = 0,
    titleName: String = ""
) : QuickViewModelFragment<VM>(layoutId = layoutId, titleName = titleName) {
    private var mRecyclerView: QuickRecyclerView? = null

    @CallSuper
    override fun initView(view: View?) {
        if (view != null) {
            mRecyclerView = view.findViewById(if (listId == 0) R.id.recyclerView else listId)
        }
        super.initView(view)
    }

    open fun getRecyclerView(): QuickRecyclerView? {
        return mRecyclerView
    }
}