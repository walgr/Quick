package com.wpf.app.quick.widgets.recyclerview.data

import androidx.annotation.LayoutRes

/**
 * Created by 王朋飞 on 2022/9/2.
 * 多选管理器 继承后可定制
 * 单选---全局-局部（同父）
 * 多选---全局-局部（同父）
 *      ---数量限制---超出反馈
 * 清空---全局-局部（同父）
 * 首次默认选中
 * 中途设置
 */
open class QuickMultiSelectData(
    open var canCancel: Boolean = true,                  //是否可以取消选择
    open var singleSelect: Boolean = true,               //true 单选  false 多选
    open val isGlobal: Boolean = true,                   //true 全局范围  false 同父范围
    open var maxLimit: Int = 5,                          //多选最多数量
    open val maxLimitListener: MaxLimitListener? = null, //超出反馈
    override var id: String? = null,
    override var name: String? = null,
    override var isSelect: Boolean = false,
    override var defaultSelect: Boolean = false,        //是否默认选中，true清空后会再次选中
    @LayoutRes override val layoutId: Int,
) : QuickSelectData(
    layoutId = layoutId,
    id = id,
    name = name,
    isSelect = isSelect,
    defaultSelect = defaultSelect
)

interface MaxLimitListener {
    fun beyondLimit(limitSize: Int)
}