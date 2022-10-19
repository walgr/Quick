package com.wpf.app.quick.widgets.selectview.listeners

import com.wpf.app.quick.widgets.selectview.data.QuickChildSelectData
import com.wpf.app.quick.widgets.selectview.data.QuickParentSelectData
import com.wpf.app.quick.widgets.selectview.data.QuickSelectData
import com.wpf.app.quickrecyclerview.listeners.DataChangeAdapter


/**
 * Created by 王朋飞 on 2022/9/2.
 * 处理父子同级
 */
interface DataSelectOnAdapter : DataChangeAdapter, SetSelectChange {

    /**
     * Item点击
     * 作为父项选中
     */
    fun onParentChild(parentSelectData: QuickParentSelectData) {
        parentSelectData.childList?.forEach {
            it.isSelect = parentSelectData.isSelect
        }
        parentSelectData.onChildChange(parentSelectData.getChildSelectList())
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
                changePos.add(getData()?.indexOf(childSelectData) ?: -1)
            }
        } else {
            if (getItemSelectSize() < childSelectData.maxLimit) {
                childSelectData.isSelect = true
                changePos.add(getData()?.indexOf(childSelectData) ?: -1)
            } else {
                childSelectData.maxLimitListener?.beyondLimit(childSelectData.maxLimit)
            }
        }
        if (curItemSelect != childSelectData.isSelect) {
            childSelectData.onSelectChange(childSelectData.isSelect)
            getSelectAdapter().getOnSelectChangeListener()?.onSelectChange()
            childSelectData.parent?.onChildChange(getSelectList())
        }
        notifyItemChange()
    }

    /**
     * 全局清空
     */
    fun clearAll(dealChange: Boolean = true) {
        val changePos = arrayListOf<Int>()
        asChildSelectData()?.forEach {
            if (it.isSelect != it.defaultSelect) {
                changePos.add(getData()?.indexOf(it) ?: -1)
            }
            it.isSelect = it.defaultSelect
            it.childList?.forEach { child ->
                if (child.isSelect != child.defaultSelect) {
                    changePos.add(getData()?.indexOf(child) ?: -1)
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
        asParentSelectData()?.find {
            it == childSelectData.parent
        }?.let {
            val changePos = arrayListOf<Int>()
            if (it.isSelect != it.defaultSelect) {
                changePos.add(getData()?.indexOf(it) ?: -1)
            }
            it.isSelect = it.defaultSelect
            it.childList?.forEach { child ->
                if (child.isSelect != child.defaultSelect) {
                    changePos.add(getData()?.indexOf(child) ?: -1)
                }
                child.isSelect = child.defaultSelect
            }
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
            getAdapter().notifyDataSetChanged()
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
            changePos.add(getData()?.indexOf(it) ?: -1)
            it.isSelect = true
        }
        if (dealChange) {
            notifyItemChange(changePos)
        }
    }

    fun notifyItemChange() {
        getAdapter().notifyDataSetChanged()
    }

    fun notifyItemChange(changePos: List<Int>) {
        changePos.forEach {
            if (it > -1) getAdapter().notifyItemChanged(it)
        }
    }

    override fun getSelectList(): MutableList<QuickChildSelectData>? {
        return asChildSelectData()?.filter {
            it.isSelect
        }?.toMutableList()
    }

    fun getItemSelectSize(): Int {
        return getSelectList()?.size ?: 0
    }

    fun getSelectAdapter(): com.wpf.app.quick.widgets.selectview.QuickSelectAdapter {
        return getAdapter() as com.wpf.app.quick.widgets.selectview.QuickSelectAdapter
    }

    fun asSelectData(): MutableList<QuickSelectData>? {
        return getData() as? MutableList<QuickSelectData>
    }

    fun asChildSelectData(): MutableList<QuickChildSelectData>? {
        return getData() as? MutableList<QuickChildSelectData>
    }

    fun asParentSelectData(): MutableList<QuickParentSelectData>? {
        return getData() as? MutableList<QuickParentSelectData>
    }
}