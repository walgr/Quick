package com.wpf.app.quick.widgets.recyclerview.listeners

import com.wpf.app.quick.widgets.recyclerview.QuickAdapter
import com.wpf.app.quick.widgets.recyclerview.data.QuickItemData

/**
 * Created by 王朋飞 on 2022/7/18.
 *
 */
interface DataChangeAdapter {

    fun getAdapter(): QuickAdapter

    fun size(): Int {
        return getAdapter().itemCount
    }

    fun setNewData(mDataList: List<QuickItemData>?) {
        cleanAll()
        appendList(mDataList)
        getAdapter().notifyDataSetChanged()
    }

    fun cleanAll() {
        getData()?.clear()
        getAdapter().notifyDataSetChanged()
    }

    fun appendList(mDataList: List<QuickItemData>?) {
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

    fun <T : QuickItemData> getRealTypeData(): MutableList<T>? {
        return getData() as? MutableList<T>
    }

    fun getData(): MutableList<QuickItemData>? {
        return getAdapter().mDataList
    }

    fun setData(newData: MutableList<QuickItemData>) {
        getAdapter().mDataList = newData
    }
}