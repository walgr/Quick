package com.wpf.app.quick.widgets.selectview.data

import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quick.widgets.recyclerview.QuickAdapter
import com.wpf.app.quick.widgets.recyclerview.QuickSelectAdapter
import com.wpf.app.quick.widgets.recyclerview.data.QuickBindData
import com.wpf.app.quick.widgets.recyclerview.holder.QuickViewHolder
import com.wpf.app.quickbind.QuickBind

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickSelectData(
    open var id: String? = null,
    open var name: String? = null,
    open var isSelect: Boolean = false,
    open var defaultSelect: Boolean = false,        //是否默认选中，true清空后会再次选中
    @LayoutRes override val layoutId: Int,
) : QuickBindData(layoutId) {

    @CallSuper
    override fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        super.onBindViewHolder(adapter as QuickSelectAdapter, viewHolder, position)
        onBindViewHolder(adapter, viewHolder, position)
    }

    @CallSuper
    open fun onBindViewHolder(
        adapter: QuickSelectAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {

    }

    override fun getAdapter(): QuickSelectAdapter? {
        return super.getAdapter() as? QuickSelectAdapter
    }

    /**
     * 当前点击
     */
    open fun onClick() {

    }

    /**
     * 选择状态变化
     */
    open fun onSelectChange(isSelect: Boolean) {

    }

    /**
     * 点击态变化
     */
    open fun onClickChange(clicked: Boolean) {

    }
}