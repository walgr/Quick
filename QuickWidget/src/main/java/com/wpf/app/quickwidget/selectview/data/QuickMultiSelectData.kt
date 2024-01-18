package com.wpf.app.quickwidget.selectview.data

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.quickutil.bind.RunOnContextWithSelf
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
    @Transient open var canCancel: Boolean = true,                  //是否可以取消选择
    @Transient open var singleSelect: Boolean = true,               //true 单选  false 多选
    @Transient open val isGlobal: Boolean = true,                   //true 全局范围  false 同父范围
    @Transient open var maxLimit: Int = 5,                          //多选最多数量
    @Transient open val maxLimitListener: MaxLimitListener? = null, //超出反馈
    @Transient override var id: String? = null,
    @Transient override var name: String? = null,
    @Transient override var isSelect: Boolean = false,
    @Transient override var defaultSelect: Boolean = false,        //是否默认选中，true清空后会再次选中
    @Transient @LayoutRes override val layoutId: Int = 0,
    @Transient override val layoutViewInContext: RunOnContextWithSelf<ViewGroup, View>? = null,
    @Transient override val isSuspension: Boolean = false,         //View是否悬浮置顶
) : QuickSelectData(), Serializable

interface MaxLimitListener {
    fun beyondLimit(limitSize: Int)
}