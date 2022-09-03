package com.wpf.app.quick.widgets.recyclerview.listeners

import com.wpf.app.quick.widgets.recyclerview.data.QuickChildSelectData
import com.wpf.app.quick.widgets.recyclerview.data.QuickParentSelectData
import com.wpf.app.quick.widgets.recyclerview.data.QuickSelectData

/**
 * Created by 王朋飞 on 2022/9/2.
 * 处理父子同级
 */
interface DataSelectOnAdapter : DataChangeAdapter {

    fun setOnSelectChange(onSelectChange: OnSelectOnChange)
    fun getOnSelectChange(): OnSelectOnChange?

    /**
     * 父Item点击
     */
    fun onParentChild(parentSelectData: QuickParentSelectData) {
        if (parentSelectData.onItemClick != null) return
        parentSelectData.childList?.forEach {
            it.isSelect = true
        }
        parentSelectData.onSelectResultChange()
        getAdapter().notifyDataSetChanged()
    }

    /**
     * 子Item点击
     */
    fun onChildClick(childSelectData: QuickChildSelectData) {
        if (childSelectData.onItemClick != null) return
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
            if (childSelectData.canCancel) {
                childSelectData.isSelect = false
                changePos.add(getData()?.indexOf(childSelectData) ?: -1)
            }
        } else {
            if (getSelectSize() < childSelectData.maxLimit) {
                childSelectData.isSelect = true
                changePos.add(getData()?.indexOf(childSelectData) ?: -1)
            } else {
                childSelectData.maxLimitListener?.beyondLimit(childSelectData.maxLimit)
            }
        }
        childSelectData.onSelectResultChange()
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
        asChildSelectData()?.find {
            it.parentId == childSelectData.parentId
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
        asChildSelectData()?.filter {
            it.parentId == childSelectData.parentId
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

    fun getSelectList(): MutableList<QuickChildSelectData>? {
        return asChildSelectData()?.filter {
            it.isSelect
        }?.toMutableList()
    }

    fun getSelectSize(): Int {
        return getSelectList()?.size ?: 0
    }

    fun asSelectData(): MutableList<QuickSelectData>? {
        return getData() as? MutableList<QuickSelectData>
    }

    fun asChildSelectData(): MutableList<QuickChildSelectData>? {
        return getData() as? MutableList<QuickChildSelectData>
    }
}