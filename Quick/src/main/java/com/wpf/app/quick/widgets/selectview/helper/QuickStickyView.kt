package com.wpf.app.quick.widgets.selectview.helper

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.widgets.recyclerview.QuickAdapter
import com.wpf.app.quick.widgets.recyclerview.data.QuickItemData
import com.wpf.app.quickutil.recyclerview.StickyView

class QuickStickyView: StickyView {

    override fun isStickyView(recyclerView: RecyclerView, view: View?): Boolean {
        val viewData = getViewData(recyclerView, view)
        if (viewData is QuickItemData) {
            return viewData.isSuspension
        }
        return view?.tag == "true"
    }

    override fun getStickViewType(recyclerView: RecyclerView, view: View?): Int {
        val viewData = getViewData(recyclerView, view)
        if (viewData is QuickItemData) {
            return viewData.viewType
        }
        return -1
    }

    private fun getViewData(recyclerView: RecyclerView, view: View?): QuickItemData? {
        if (view == null) return null
        val viewPos = recyclerView.getChildAdapterPosition(view)
        val quickAdapter = recyclerView.adapter as? QuickAdapter
        return quickAdapter?.mDataList?.getOrNull(viewPos)
    }
}