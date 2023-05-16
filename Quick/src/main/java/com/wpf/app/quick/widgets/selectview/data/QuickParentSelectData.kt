package com.wpf.app.quick.widgets.selectview.data

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.LayoutRes
import com.wpf.app.quick.widgets.selectview.QuickSelectAdapter
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickbind.interfaces.RunItemClickWithSelf
import com.wpf.app.quickbind.interfaces.RunOnContext
import com.wpf.app.quickutil.recyclerview.scrollToPositionAndOffset

/**
 * Created by 王朋飞 on 2022/7/13.
 * 筛选父类
 */
open class QuickParentSelectData(
    open var canClick: Boolean = false,
    override val isSuspension: Boolean = false,                 //父View是否悬浮置顶
    override var parent: QuickParentSelectData? = null,
    override var childList: MutableList<out QuickChildSelectData>? = null,
    onParentClick: RunItemClickWithSelf<QuickParentSelectData>? = null,
    override var id: String? = null,
    override var name: String? = null,
    override var defaultSelect: Boolean = false,
    override var isSelect: Boolean = defaultSelect,
    override var canCancel: Boolean = true,                  //是否可以取消选择
    override var singleSelect: Boolean = true,               //true 单选  false 多选
    override val isGlobal: Boolean = true,                   //true 全局范围  false 同父范围
    override var maxLimit: Int = 5,                          //多选最多数量
    @Transient
    override val maxLimitListener: MaxLimitListener? = null, //超出反馈
    @LayoutRes override val layoutId: Int = 0,
    @Transient override val layoutView: View? = null,
    @Transient override val layoutViewInContext: RunOnContext<View>? = null,
) : QuickChildSelectData(
    onChildClick = onParentClick as? RunItemClickWithSelf<QuickChildSelectData>,
) {

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick() {
        if (getView() != null) {
            if (getAdapter()?.curClickData != this) {
                val oldClickPos = getAdapter()?.getDataPos(getAdapter()?.curClickData) ?: -1
                getAdapter()?.curClickData = this
                getAdapter()?.notifyItemChange(
                    arrayListOf(
                        oldClickPos,
                        getAdapter()?.getDataPos(this) ?: -1
                    )
                )
            }
        }
        if (!isInOne) {
            childList?.let {
                getAdapter()?.childSelectAdapter?.setNewData(childList)
            }
        } else {
            //在右边查询和点击的id相同的item
            val childAdapter = getAdapter()?.childSelectAdapter ?: return
            var findPos = -1
            for (i in 0 until childAdapter.size()) {
                findPos++
                val child = childAdapter.getRealTypeData<QuickChildSelectData>()?.get(i)
                if (child?.parent?.id == this.id) {
                    break
                }
            }
            if (findPos >= 0) {
                childAdapter.mRecyclerView?.scrollToPositionAndOffset(findPos)
            }
        }
    }

    open fun onSelectChildChange(selectList: List<QuickChildSelectData>?) {

    }

    /**
     * 返回父在子列表作为标题View
     */
    open fun asTitleViewInChild(): QuickParentSelectData? {
        return null
    }

    override fun onBindViewHolder(
        adapter: QuickSelectAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        super.onBindViewHolder(adapter, viewHolder, position)
        asTitleViewInChild()?.onBindViewHolder(adapter, viewHolder, position)
        onClickChange(adapter.curClickData == this)
    }
}