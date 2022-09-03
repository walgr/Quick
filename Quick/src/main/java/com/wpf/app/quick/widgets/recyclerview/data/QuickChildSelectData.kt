package com.wpf.app.quick.widgets.recyclerview.data

import androidx.annotation.LayoutRes
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.helper.binddatahelper.ItemClick
import com.wpf.app.quickbind.interfaces.itemClickRun
import com.wpf.app.quickbind.interfaces.itemClickWithSelf

/**
 * Created by 王朋飞 on 2022/7/13.
 * 筛选子类
 */
open class QuickChildSelectData(
    open var parentId: String? = null,
    open var childList: MutableList<out QuickChildSelectData>? = null,
    open val onItemClick: ItemClick? = null,
    override var id: String? = null,
    override var name: String? = null,
    override var isSelect: Boolean = false,
    override var defaultSelect: Boolean = false,
    override var canCancel: Boolean = true,                  //是否可以取消选择
    override var singleSelect: Boolean = true,               //true 单选  false 多选
    override val isGlobal: Boolean = true,                   //true 全局范围  false 同父范围
    override var maxLimit: Int = 5,                          //多选最多数量
    override val maxLimitListener: MaxLimitListener? = null, //超出反馈
    @LayoutRes override val layoutId: Int,
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
    layoutId = layoutId
) {

    @BindData2View(helper = ItemClick::class)
    open val itemClick = itemClickWithSelf<QuickChildSelectData> { self ->
        itemClickRun {
            getAdapter().onChildClick(self)
        }
    }

    open fun onChange() {
        getAdapter().getOnSelectChange()?.onSelect(getAdapter().getSelectList())
    }
}