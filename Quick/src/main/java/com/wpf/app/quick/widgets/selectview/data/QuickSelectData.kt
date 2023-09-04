package com.wpf.app.quick.widgets.selectview.data

import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quick.widgets.selectview.QuickSelectAdapter
import com.wpf.app.quickutil.bind.RunOnContextWithSelf
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.data.QuickClickData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickSelectData(
    @Transient open var id: String? = null,
    @Transient open var name: String? = null,
    @Transient open var defaultSelect: Boolean = false,            //是否默认选中，true 清空后会再次选中
    @Transient open var isSelect: Boolean = defaultSelect,
    @Transient @LayoutRes override val layoutId: Int = 0,
    @Transient override val layoutViewInContext: RunOnContextWithSelf<ViewGroup, View>? = null,
    @Transient override val isSuspension: Boolean = false,         //View是否悬浮置顶
) : QuickClickData(), Serializable {

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