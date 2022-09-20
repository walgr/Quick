package com.wpf.app.quick.widgets.selectview.data

import android.annotation.SuppressLint
import androidx.annotation.LayoutRes
import com.wpf.app.quick.utils.LogUtil
import com.wpf.app.quick.widgets.recyclerview.QuickAdapter
import com.wpf.app.quick.widgets.recyclerview.QuickSelectAdapter
import com.wpf.app.quick.widgets.recyclerview.data.MaxLimitListener
import com.wpf.app.quick.widgets.recyclerview.data.QuickBindData
import com.wpf.app.quick.widgets.recyclerview.holder.QuickViewHolder
import com.wpf.app.quickbind.interfaces.RunItemClickWithSelf

/**
 * Created by 王朋飞 on 2022/7/13.
 * 筛选父类
 */
open class QuickParentSelectData(
    open var canClick: Boolean = false,
    override var parent: QuickParentSelectData? = null,
    override var childList: MutableList<out QuickChildSelectData>? = null,
    onParentClick: RunItemClickWithSelf<QuickParentSelectData>? = null,
    override var id: String? = null,
    override var name: String? = null,
    override var isSelect: Boolean = false,
    override var defaultSelect: Boolean = false,
    override var canCancel: Boolean = true,                  //是否可以取消选择
    override var singleSelect: Boolean = true,               //true 单选  false 多选
    override val isGlobal: Boolean = true,                   //true 全局范围  false 同父范围
    override var maxLimit: Int = 5,                          //多选最多数量
    @Transient
    override val maxLimitListener: MaxLimitListener? = null, //超出反馈
    @LayoutRes override val layoutId: Int,
) : QuickChildSelectData(
    onChildClick = onParentClick as? RunItemClickWithSelf<QuickChildSelectData>,
    layoutId = layoutId
) {

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick() {
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
        childList?.let {
            getAdapter()?.childSelectAdapter?.setNewData(childList)
        }
    }

    open fun onChildChange(selectList: List<QuickChildSelectData>?) {

    }

    override fun onBindViewHolder(
        adapter: QuickSelectAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        super.onBindViewHolder(adapter, viewHolder, position)
        onClickChange(adapter.curClickData == this)
    }
}