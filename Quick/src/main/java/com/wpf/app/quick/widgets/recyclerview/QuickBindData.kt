package com.wpf.app.quick.widgets.recyclerview

import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.QuickBind.dealInPlugins

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickBindData(
    @LayoutRes open var layoutId: Int = 0,
    override var viewType: Int = 0
) : QuickItemData(viewType = viewType) {

    private lateinit var mViewHolder: QuickViewHolder<QuickBindData>
    private lateinit var mAdapter: QuickAdapter
    private var dealBind = true

    @CallSuper
    open fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        mAdapter = adapter
        mViewHolder = viewHolder
        if (dealBind) {
            dealInPlugins(this, null, QuickBind.bindPlugin)
        }
    }

    fun noBind() {
        this.dealBind = false
    }

    fun getAdapter(): QuickAdapter {
        return mAdapter
    }

    fun getViewHolder(): QuickViewHolder<QuickBindData> {
        return mViewHolder
    }
}