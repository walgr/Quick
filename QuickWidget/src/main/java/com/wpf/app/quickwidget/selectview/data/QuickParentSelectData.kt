package com.wpf.app.quickwidget.selectview.data

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.nullDefault
import com.wpf.app.quickutil.run.RunItemClickWithSelf
import com.wpf.app.quickutil.run.RunOnContextWithSelf
import com.wpf.app.quickutil.widget.scrollToPositionAndOffset
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/13.
 * 筛选父类
 */
open class QuickParentSelectData(
    isSuspension: Boolean = false,                                      //父View是否悬浮置顶
    parent: QuickParentSelectData? = null,
    childList: MutableList<QuickChildSelectData>? = null,
    onParentClick: RunItemClickWithSelf<QuickParentSelectData>? = null,
    id: String? = null,
    name: String? = null,
    defaultSelect: Boolean = false,
    isSelect: Boolean = defaultSelect,
    canClickAgain: Boolean = true,                                      //选中后再次点击是否触发选中回调
    canCancel: Boolean = true,                                          //是否可以取消选择
    singleSelect: Boolean = true,                                       //true 单选  false 多选
    isGlobal: Boolean = true,                                           //true 全局范围  false 同父范围
    maxLimit: Int = 5,                                                  //多选最多数量
    maxLimitListener: MaxLimitListener? = null,                         //超出反馈
    layoutId: Int = 0,
    layoutViewCreate: RunOnContextWithSelf<ViewGroup, View>? = null,
    autoSet: Boolean = false,                                           //自动映射
) : QuickChildSelectData(
    parent = parent,
    childList = childList,
    onChildClick = onParentClick.asTo<RunItemClickWithSelf<QuickChildSelectData>>(),
    id = id,
    name = name,
    defaultSelect = defaultSelect,
    isSelect = isSelect,
    canClickAgain = canClickAgain,
    canCancel = canCancel,
    singleSelect = singleSelect,
    isGlobal = isGlobal,
    maxLimit = maxLimit,
    maxLimitListener = maxLimitListener,
    layoutId = layoutId,
    layoutViewCreate = layoutViewCreate,
    autoSet = autoSet,
    isSuspension = isSuspension
), Serializable {

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick() {
        if (isSelect) {
            if (!canClickAgain) return
        }
        super.onItemClick()
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
                childAdapter.getRecyclerView()?.scrollToPositionAndOffset(findPos)
            }
        }
    }

    fun getChildSelectSize(): Int {
        return childList?.count { it.isSelect }.nullDefault(0)
    }

    @Suppress("unused")
    open fun getChildSelectList(): MutableList<QuickChildSelectData>? {
        return childList?.filter { it.isSelect }?.toMutableList()
    }

    open fun onSelectChildChange(selectList: MutableList<QuickChildSelectData>?) {

    }

    /**
     * 返回父在子列表作为标题View
     */
    open fun asTitleViewInChild(): QuickParentSelectData? {
        return null
    }
}