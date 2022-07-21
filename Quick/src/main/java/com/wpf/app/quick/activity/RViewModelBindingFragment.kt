package com.wpf.app.quick.activity

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.wpf.app.quick.R
import com.wpf.app.quick.viewmodel.QuickBindingViewModel
import com.wpf.app.quick.widgets.recyclerview.QuickRecyclerView

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
abstract class RViewModelBindingFragment<VM : QuickBindingViewModel<VB>, VB : ViewDataBinding> @JvmOverloads constructor(
    @LayoutRes override val layoutId: Int = 0,
    @IdRes open val listId: Int = 0,
    override val titleName: String = ""
) : QuickViewModelBindingFragment<VM, VB>(
    layoutId, null, titleName
) {
    private var mRecyclerView: QuickRecyclerView? = null

    @CallSuper
    override fun initView(view: View?) {
        mRecyclerView = view?.findViewById(if (listId == 0) R.id.recyclerView else listId)
        super.initView(view)
    }

    override fun getTitle(): String? {
        return titleName
    }

    open fun getRecyclerView(): QuickRecyclerView? {
        return mRecyclerView
    }
}