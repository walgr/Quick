package com.wpf.app.quickrecyclerview.listeners

import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickutil.helper.generic.nullDefault

/**
 * Created by 王朋飞 on 2022/7/18.
 *
 */
interface DataAdapter {

    fun getQuickAdapter(): QuickAdapter

    fun size(): Int {
        return getQuickAdapter().itemCount
    }

    fun setNewData(mDataList: List<QuickItemData>?) {
        cleanAll()
        appendList(mDataList)
        getQuickAdapter().notifyDataSetChanged()
    }

    fun cleanAll() {
        getData()?.clear()
        getQuickAdapter().notifyDataSetChanged()
    }

    fun appendList(mDataList: List<QuickItemData>?) {
        if (mDataList == null) return
        if (getData() == null) {
            setData(arrayListOf())
        }
        getData()?.addAll(mDataList)
    }

    fun addData(mDataList: List<QuickItemData>?) {
        if (mDataList == null) return
        if (getData() == null) {
            setData(arrayListOf())
        }
        getData()?.addAll(mDataList)
    }

    fun addData(data: QuickItemData) {
        if (getData() == null) {
            setData(arrayListOf())
        }
        getData()?.add(data)
    }

    fun addData(
        pos: Int,
        data: QuickItemData
    ) {
        if (getData() == null) {
            setData(arrayListOf())
        }
        getData()?.add(pos, data)
    }

    fun getDataPos(curData: QuickItemData?): Int {
        return getData()?.indexOf(curData) ?: -1
    }

    //item拖动交换位置
    fun onMove(sourcePosition: Int, targetPosition: Int) {
        val headerSize = getQuickAdapter().headerViews.size
        if (sourcePosition < headerSize || targetPosition < headerSize) return
        val sourceItem = getData(sourcePosition) ?: return
        getData()?.removeAt(sourcePosition - headerSize)
        addData(targetPosition - headerSize, sourceItem)
        getQuickAdapter().notifyItemMoved(sourcePosition, targetPosition)
        getQuickAdapter().notifyItemChanged(sourcePosition)
        getQuickAdapter().notifyItemChanged(targetPosition)
    }

    /**
     * 未找到不会刷新
     */
    fun notifyItemChanged(curData: QuickItemData?) {
        val pos = getDataPos(curData)
        if (pos != -1) {
            getQuickAdapter().notifyItemChanged(pos)
        }
    }

    fun <T : QuickItemData> getRealTypeData(): MutableList<T>? {
        return getData() as? MutableList<T>
    }

    fun getData(): MutableList<QuickItemData>? {
        return getQuickAdapter().mDataList
    }

    fun getDataWithHeaderFooter(forceInit: Boolean = false): MutableList<QuickItemData>? {
        if (getQuickAdapter().allDataList == null || forceInit) {
            getQuickAdapter().allDataList = mutableListOf()
            getQuickAdapter().allDataList!!.apply {
                addAll(getQuickAdapter().headerViews)
                getData()?.let {
                    addAll(it)
                }
                addAll(getQuickAdapter().footerViews)
            }
        }
        return getQuickAdapter().allDataList
    }

    fun getData(position: Int): QuickItemData? {
        return getDataWithHeaderFooter(position)
    }

    fun indexOf(itemData: QuickItemData): Int {
        return getQuickAdapter().headerViews.size + getQuickAdapter().mDataList?.indexOf(itemData).nullDefault(0)
    }

    fun getDataWithHeaderFooter(position: Int): QuickItemData? {
        val realData: QuickItemData? =
            if (getQuickAdapter().headerViews.isNotEmpty() && position < getQuickAdapter().headerViews.size) {
                getQuickAdapter().headerViews[position]
            } else if (position < getQuickAdapter().headerViews.size + getQuickAdapter().mDataList?.size.nullDefault(0)) {
                getQuickAdapter().mDataList?.getOrNull(position - getQuickAdapter().headerViews.size)
            } else {
                getQuickAdapter().footerViews[position - (getQuickAdapter().headerViews.size + getQuickAdapter().mDataList?.size.nullDefault(0))]
            }
        return realData
    }

    fun setData(newData: MutableList<QuickItemData>) {
        getQuickAdapter().mDataList = newData
    }
}