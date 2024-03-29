package com.wpf.app.quickrecyclerview.listeners

import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickutil.log.LogUtil

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
        LogUtil.e("交换:${sourcePosition}-${targetPosition}")
        val sourceItem = getData()?.getOrNull(sourcePosition) ?: return
        getData()?.removeAt(sourcePosition)
        addData(targetPosition, sourceItem)
        getQuickAdapter().notifyItemMoved(sourcePosition, targetPosition)
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

    fun setData(newData: MutableList<QuickItemData>) {
        getQuickAdapter().mDataList = newData
    }
}