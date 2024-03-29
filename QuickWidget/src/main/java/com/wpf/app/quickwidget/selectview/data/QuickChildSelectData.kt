package com.wpf.app.quickwidget.selectview.data

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quickutil.bind.RunItemClickWithSelf
import com.wpf.app.quickutil.bind.RunOnContextWithSelf
import com.wpf.app.quickutil.other.asTo
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/13.
 * 筛选子类
 */
open class QuickChildSelectData(
    @Transient open var isInOne: Boolean = false,
    isSuspension: Boolean = false,                 //View是否悬浮置顶
    @Transient open var parent: QuickParentSelectData? = null,
    @Transient open var childList: MutableList<out QuickChildSelectData>? = null,
    @Transient open val onChildClick: RunItemClickWithSelf<out QuickChildSelectData>? = null,
    id: String? = null,
    name: String? = null,
    defaultSelect: Boolean = false,
    isSelect: Boolean = defaultSelect,
    canCancel: Boolean = true,                     //是否可以取消选择
    singleSelect: Boolean = false,                 //true 单选  false 多选
    isGlobal: Boolean = true,                      //true 全局范围  false 同父范围
    maxLimit: Int = 5,                             //多选最多数量
    maxLimitListener: MaxLimitListener? = null,    //超出反馈
    layoutId: Int = 0,
    layoutViewInContext: RunOnContextWithSelf<ViewGroup, View>? = null,
    isDealBinding: Boolean = false,                                 //是否处理DataBinding
    autoSet: Boolean = false,                                        //自动映射
) : QuickMultiSelectData(
    canCancel = canCancel,
    singleSelect = singleSelect,
    isGlobal = isGlobal,
    maxLimit = maxLimit,
    maxLimitListener = maxLimitListener,
    id = id,
    name = name,
    isSelect = isSelect,
    defaultSelect = defaultSelect,
    layoutId = layoutId,
    layoutViewInContext = layoutViewInContext,
    isDealBinding = isDealBinding,
    autoSet = autoSet,
    isSuspension = isSuspension
), Serializable {

    fun getChildSelectSize(): Int {
        return getChildSelectList()?.size ?: 0
    }

    fun getChildSelectList(): List<QuickChildSelectData>? {
        return childList?.filter { it.isSelect }
    }

    override fun onClick() {
        onItemClick()
        if (this is QuickParentSelectData) {
            if (!canClick) {
                return
            }
            getAdapter()?.onChildClick(this)
            getAdapter()?.onParentChild(this)
        } else {
            getAdapter()?.onChildClick(this)
        }
        getViewHolder()?.itemView?.let {
            onChildClick?.asTo<RunItemClickWithSelf<QuickChildSelectData>>()?.run(it, this)?.onClick(it)
        }
    }

    open fun onItemClick() {

    }

    override fun onSelectChange(isSelect: Boolean) {

    }
}