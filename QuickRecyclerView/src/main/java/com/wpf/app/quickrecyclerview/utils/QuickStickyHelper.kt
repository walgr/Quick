package com.wpf.app.quickrecyclerview.utils

import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickSuspensionData

class QuickStickyHelper : StickyHelper {

    override fun getAllStickyList(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?): MutableList<Int> {
        val result = mutableListOf<Int>()
        if (adapter == null) return result
        (0 until adapter.itemCount).forEach { i ->
            if (getSuspendData(adapter, i)?.isSuspension == true) {
                result.add(i)
            }
        }
        return result
    }

    private fun getSuspendData(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?, viewPos: Int): QuickSuspensionData? {
        val quickAdapter = adapter as? QuickAdapter
        return quickAdapter?.getDataByViewType(quickAdapter.getItemViewType(viewPos)) as? QuickSuspensionData
    }
}