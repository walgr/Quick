package com.wpf.app.quick.widgets.selectview.data

import android.view.View
import androidx.annotation.LayoutRes
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quickbind.helper.binddatahelper.ItemClick
import com.wpf.app.quickbind.interfaces.RunItemClickWithSelf
import com.wpf.app.quickbind.interfaces.itemClickRun
import com.wpf.app.quickbind.interfaces.itemClickWithSelf

/**
 * Created by 王朋飞 on 2022/7/13.
 * 筛选子类
 */
open class QuickChildSelectData(
    open var isInOne: Boolean = false,
    open var parent: QuickParentSelectData? = null,
    override val isSuspension: Boolean = false,                 //View是否悬浮置顶
    open var childList: MutableList<out QuickChildSelectData>? = null,
    onChildClick: RunItemClickWithSelf<QuickChildSelectData>? = null,
    override var id: String? = null,
    override var name: String? = null,
    override var defaultSelect: Boolean = false,
    override var isSelect: Boolean = defaultSelect,
    override var canCancel: Boolean = true,                     //是否可以取消选择
    override var singleSelect: Boolean = false,                 //true 单选  false 多选
    override val isGlobal: Boolean = true,                      //true 全局范围  false 同父范围
    override var maxLimit: Int = 5,                             //多选最多数量
    @Transient
    override val maxLimitListener: MaxLimitListener? = null,    //超出反馈
    @LayoutRes override val layoutId: Int = 0,
    override val layoutView: View? = null,
) : QuickMultiSelectData(
    layoutId = layoutId,
    layoutView = layoutView,
    isSuspension = isSuspension
) {

    fun getChildSelectSize(): Int {
        return getChildSelectList()?.size ?: 0
    }

    fun getChildSelectList(): List<QuickChildSelectData>? {
        return childList?.filter { it.isSelect }
    }

    private val childClick = itemClickWithSelf<QuickChildSelectData> { self ->
        itemClickRun {
            onClick()
            if (self is QuickParentSelectData) {
                if (!self.canClick) {
                    return@itemClickRun
                }
                getAdapter()?.onChildClick(self)
                getAdapter()?.onParentChild(self)
            } else {
                getAdapter()?.onChildClick(self)
            }
        }
    }

    @BindData2View(helper = ItemClick::class)
    open val itemClick = onChildClick ?: childClick

    override fun onSelectChange(isSelect: Boolean) {

    }

    fun performClick() {
        childClick.run(this)
    }
}