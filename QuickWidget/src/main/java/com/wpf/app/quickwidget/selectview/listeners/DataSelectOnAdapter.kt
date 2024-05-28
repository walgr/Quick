package com.wpf.app.quickwidget.selectview.listeners

import com.wpf.app.quickrecyclerview.listeners.DataAdapter
import com.wpf.app.quickutil.other.nullDefault
import com.wpf.app.quickwidget.selectview.QuickSelectAdapter
import com.wpf.app.quickwidget.selectview.data.QuickChildSelectData
import com.wpf.app.quickwidget.selectview.data.QuickParentSelectData
import com.wpf.app.quickwidget.selectview.data.QuickSelectData


/**
 * Created by 王朋飞 on 2022/9/2.
 * 处理父子同级
 */
interface DataSelectOnAdapter : DataAdapter, SetSelectChange {

    /**
     * Item点击
     * 作为父项选中
     */
    fun onParentChild(parentSelectData: QuickParentSelectData) {
        parentSelectData.childList?.forEach {
            it.isSelect = parentSelectData.isSelect
        }
        parentSelectData.onSelectChildChange(parentSelectData.getChildSelectList())
    }

    /**
     * Item点击
     * 作为子项选中
     */
    fun onChildClick(childSelectData: QuickChildSelectData) {
        val changePos = arrayListOf<Int>()
        val curItemSelect = childSelectData.isSelect
        if (childSelectData.singleSelect) {
            if (childSelectData.isGlobal) {
                clearAll(false)
            } else {
                clearInParent(childSelectData, false)
            }
        }
        if (curItemSelect) {
            childSelectData.isSelect = !childSelectData.canCancel
            if (!childSelectData.isSelect) {
                changePos.add(indexOf(childSelectData))
            }
        } else {
            if (getItemSelectSize(if (childSelectData.isGlobal) null else childSelectData.parent?.id) < childSelectData.maxLimit) {
                childSelectData.isSelect = true
                changePos.add(indexOf(childSelectData))
            } else {
                childSelectData.maxLimitListener?.beyondLimit(childSelectData.maxLimit)
            }
        }
        if (curItemSelect != childSelectData.isSelect) {
            childSelectData.onSelectChange(childSelectData.isSelect)
        }
        getSelectAdapter().getOnSelectChangeListener()?.onSelectChange()
        childSelectData.parent?.onSelectChildChange(getSelectList(childSelectData.parent?.id))
        notifyItemChange()
    }

    /**
     * 全局清空
     */
    fun clearAll(dealChange: Boolean = true) {
        val changePos = arrayListOf<Int>()
        asChildSelectData()?.forEach {
            if (it.isSelect != it.defaultSelect) {
                changePos.add(indexOf(it))
            }
            it.isSelect = it.defaultSelect
            it.childList?.forEach { child ->
                if (child.isSelect != child.defaultSelect) {
                    changePos.add(indexOf(child))
                }
                child.isSelect = child.defaultSelect
            }
        }
        if (dealChange) {
            notifyItemChange(changePos)
        }
    }

    /**
     * 同父清空
     */
    fun clearInParent(
        childSelectData: QuickChildSelectData,
        dealChange: Boolean = true,
    ) {
        asChildSelectData()?.filter { it.parent == childSelectData.parent }?.forEach {
            val changePos = arrayListOf<Int>()
            if (it.isSelect != it.defaultSelect) {
                changePos.add(indexOf(it))
            }
            it.isSelect = it.defaultSelect
            if (dealChange) {
                notifyItemChange(changePos)
            }
        }
    }

    /**
     * 全局全选
     */
    fun selectAll(dealChange: Boolean = true) {
        asSelectData()?.forEach {
            it.isSelect = true
        }
        if (dealChange) {
            getQuickAdapter().notifyDataSetChanged()
        }
    }

    /**
     * 同父全选
     */
    fun selectInParent(childSelectData: QuickChildSelectData, dealChange: Boolean = true) {
        val changePos = arrayListOf<Int>()
        asParentSelectData()?.filter {
            it == childSelectData.parent
        }?.forEach {
            changePos.add(indexOf(it))
            it.isSelect = true
        }
        if (dealChange) {
            notifyItemChange(changePos)
        }
    }

    fun notifyItemChange() {
        getQuickAdapter().notifyDataSetChanged()
    }

    fun notifyItemChange(changePos: List<Int>) {
        changePos.forEach {
            if (it > -1) getQuickAdapter().notifyItemChanged(it)
        }
    }

    override fun getSelectList(parentId: String?): MutableList<QuickChildSelectData>? {
        return asChildSelectData()?.filter {
            it.isSelect && if (parentId.isNullOrEmpty()) true else it.parent?.id == parentId
        }?.toMutableList()
    }

    fun getItemSelectSize(parentId: String? = null): Int {
        return getSelectList(parentId)?.size.nullDefault(0)
    }

    fun getSelectAdapter(): QuickSelectAdapter {
        return getQuickAdapter() as QuickSelectAdapter
    }

    fun asSelectData(): MutableList<QuickSelectData>? {
        return getData()?.filterIsInstance<QuickSelectData>()?.toMutableList()
    }

    fun asChildSelectData(): MutableList<QuickChildSelectData>? {
        return getData()?.filterIsInstance<QuickChildSelectData>()?.toMutableList()
    }

    fun asParentSelectData(): MutableList<QuickParentSelectData>? {
        return getData()?.filterIsInstance<QuickParentSelectData>()?.toMutableList()
    }
}