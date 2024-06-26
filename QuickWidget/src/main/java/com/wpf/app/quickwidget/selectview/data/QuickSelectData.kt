package com.wpf.app.quickwidget.selectview.data

import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.data.QuickClickData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.run.RunOnContextWithSelf
import com.wpf.app.quickwidget.selectview.QuickSelectAdapter
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickSelectData(
    open var id: String? = null,
    open var name: String? = null,
    open var defaultSelect: Boolean = false,                        //是否默认选中，true 清空后会再次选中
    open var isSelect: Boolean = defaultSelect,
    open var canClickAgain: Boolean = true,                         //选中后再次点击是否触发选中回调
    layoutId: Int = 0,
    layoutViewCreate: RunOnContextWithSelf<ViewGroup, View>? = null,
    autoSet: Boolean = false,                                        //自动映射
    isSuspension: Boolean = false,                                  //View是否悬浮置顶
) : QuickClickData(
    layoutId = layoutId,
    layoutViewCreate = layoutViewCreate,
    autoSet = autoSet,
    isSuspension = isSuspension,
), Serializable {

    override fun onClick(view: View) {

    }

    @CallSuper
    override fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int,
    ) {
        super.onBindViewHolder(adapter, viewHolder, position)
        if (adapter is QuickSelectAdapter) {
            onBindViewHolder(adapter, viewHolder, position)
        }
    }

    open fun onBindViewHolder(
        adapter: QuickSelectAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int,
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
}