package com.wpf.app.quickrecyclerview.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.data.QuickSuspensionData

class QuickStickyView : StickyView {

    override fun isStickyView(recyclerView: RecyclerView, curView: View?): Boolean {
        val viewData = getViewData(recyclerView, curView)
        if (viewData is QuickSuspensionData) {
            return viewData.isSuspension
        }
        return false
    }

    override fun getAllStickyList(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?): List<Int> {
        val result = arrayListOf<Int>()
        if (adapter == null) return result
        (0 until adapter.itemCount).forEach { i ->
            if (getViewData(adapter, i)?.isSuspension == true) {
                result.add(i)
            }
        }
        return result
    }

    override fun getStickViewType(recyclerView: RecyclerView, view: View?): Int {
        val viewData = getViewData(recyclerView, view)
        if (viewData is QuickItemData) {
            return viewData.viewType
        }
        return -1
    }

    private fun getViewData(recyclerView: RecyclerView, view: View?): QuickSuspensionData? {
        if (view == null) return null
        return getViewData(adapter = recyclerView.adapter, recyclerView.getChildAdapterPosition(view))
    }

    private fun getViewData(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?, viewPos: Int): QuickSuspensionData? {
        val quickAdapter = adapter as? QuickAdapter
        return quickAdapter?.getDataByViewType(quickAdapter.getItemViewType(viewPos)) as? QuickSuspensionData
    }
}