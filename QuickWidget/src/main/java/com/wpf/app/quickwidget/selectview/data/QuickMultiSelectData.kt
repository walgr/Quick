package com.wpf.app.quickwidget.selectview.data

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quickutil.run.RunOnContextWithSelf
import java.io.Serializable

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
    @Transient open val maxLimitListener: MaxLimitListener? = null, //超出反馈
    id: String? = null,
    name: String? = null,
    isSelect: Boolean = false,
    defaultSelect: Boolean = false,                                 //是否默认选中，true清空后会再次选中
    layoutId: Int = 0,
    layoutViewCreate: RunOnContextWithSelf<ViewGroup, View>? = null,
    autoSet: Boolean = false,                                        //自动映射
    isSuspension: Boolean = false,                                  //View是否悬浮置顶
) : QuickSelectData(
    id = id,
    name = name,
    defaultSelect = defaultSelect,
    isSelect = isSelect,
    layoutId = layoutId,
    layoutViewCreate = layoutViewCreate,
    autoSet = autoSet,
    isSuspension = isSuspension,
), Serializable

interface MaxLimitListener {
    fun beyondLimit(limitSize: Int)
}