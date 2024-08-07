package com.wpf.app.quickwidget.selectview.data

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quickutil.helper.generic.asTo
import com.wpf.app.quickutil.run.RunItemClickWithSelf
import com.wpf.app.quickutil.run.RunOnContextWithSelf
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/13.
 * 筛选子类
 */
open class QuickChildSelectData(
    @Transient open var isInOne: Boolean = false,
    isSuspension: Boolean = false,                 //View是否悬浮置顶
    @Transient open var parent: QuickParentSelectData? = null,
    @Transient open var childList: MutableList<QuickChildSelectData>? = null,
    @Transient open val onChildClick: RunItemClickWithSelf<QuickChildSelectData>? = null,
    id: String? = null,
    name: String? = null,
    defaultSelect: Boolean = false,
    isSelect: Boolean = defaultSelect,
    canClickAgain: Boolean = true,                 //选中后再次点击是否触发选中回调
    canCancel: Boolean = true,                     //是否可以取消选择
    singleSelect: Boolean = false,                 //true 单选  false 多选
    isGlobal: Boolean = true,                      //true 全局范围  false 同父范围
    maxLimit: Int = 5,                             //多选最多数量
    maxLimitListener: MaxLimitListener? = null,    //超出反馈
    layoutId: Int = 0,
    layoutViewCreate: RunOnContextWithSelf<ViewGroup, View>? = null,
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
    canClickAgain = canClickAgain,
    layoutId = layoutId,
    layoutViewCreate = layoutViewCreate,
    autoSet = autoSet,
    isSuspension = isSuspension
), Serializable {

    override fun onClick(view: View) {
        onItemClick()
    }

    open fun onItemClick() {
        getAdapter()?.onChildClick(this)
        getViewHolder()?.itemView?.let {
            onChildClick?.asTo<RunItemClickWithSelf<QuickChildSelectData>>()?.run(it, this)?.onClick(it)
        }
    }
}