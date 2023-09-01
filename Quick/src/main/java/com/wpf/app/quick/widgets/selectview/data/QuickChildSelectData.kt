package com.wpf.app.quick.widgets.selectview.data

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.interfaces.RunItemClickWithSelf
import com.wpf.app.quickbind.interfaces.RunOnContext
import com.wpf.app.quickbind.interfaces.RunOnContextWithSelf
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/13.
 * 筛选子类
 */
open class QuickChildSelectData(
    @Transient open var isInOne: Boolean = false,
    @Transient open var parent: QuickParentSelectData? = null,
    @Transient override val isSuspension: Boolean = false,                 //View是否悬浮置顶
    @Transient open var childList: MutableList<out QuickChildSelectData>? = null,
    @Transient open val onChildClick: RunItemClickWithSelf<QuickChildSelectData>? = null,
    @Transient override var id: String? = null,
    @Transient override var name: String? = null,
    @Transient override var defaultSelect: Boolean = false,
    @Transient override var isSelect: Boolean = defaultSelect,
    @Transient override var canCancel: Boolean = true,                     //是否可以取消选择
    @Transient override var singleSelect: Boolean = false,                 //true 单选  false 多选
    @Transient override val isGlobal: Boolean = true,                      //true 全局范围  false 同父范围
    @Transient override var maxLimit: Int = 5,                             //多选最多数量
    @Transient override val maxLimitListener: MaxLimitListener? = null,    //超出反馈
    @Transient @LayoutRes override val layoutId: Int = 0,
    @Transient override val layoutViewInContext: RunOnContextWithSelf<View, ViewGroup>? = null,
) : QuickMultiSelectData(), Serializable {

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
            onChildClick?.run(it, this)?.onClick(it)
        }
    }

    open fun onItemClick() {

    }

    override fun onSelectChange(isSelect: Boolean) {

    }
}