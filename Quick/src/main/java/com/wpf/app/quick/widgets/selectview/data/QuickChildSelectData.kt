package com.wpf.app.quick.widgets.selectview.data

import android.view.View
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.interfaces.RunItemClickWithSelf
import com.wpf.app.quickbind.interfaces.RunOnContext
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/13.
 * 筛选子类
 */
open class QuickChildSelectData(
    open var isInOne: Boolean = false,
    open var parent: QuickParentSelectData? = null,
    override val isSuspension: Boolean = false,                 //View是否悬浮置顶
    open var childList: MutableList<out QuickChildSelectData>? = null,
    open val onChildClick: RunItemClickWithSelf<QuickChildSelectData>? = null,
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
    @Transient override val layoutView: View? = null,
    @Transient override val layoutViewInContext: RunOnContext<View>? = null,
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