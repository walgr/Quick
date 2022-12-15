package com.wpf.app.quick.widgets.selectview.data

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quick.widgets.selectview.QuickSelectAdapter
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.data.QuickClickData
import com.wpf.app.quickrecyclerview.data.QuickStateData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickSelectData(
    open var id: String? = null,
    open var name: String? = null,
    open var defaultSelect: Boolean = false,            //是否默认选中，true 清空后会再次选中
    open var isSelect: Boolean = defaultSelect,
    @LayoutRes override val layoutId: Int = 0,
    @Transient override val layoutView: View? = null,
    override val isSuspension: Boolean = false,         //View是否悬浮置顶
) : QuickClickData() {

    override fun onClick() {

    }

    @CallSuper
    override fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        super.onBindViewHolder(adapter as QuickSelectAdapter, viewHolder, position)
        onBindViewHolder(adapter, viewHolder, position)
    }

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