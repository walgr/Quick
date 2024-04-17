package com.wpf.app.quickwidget.list.tag

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.wpf.app.quickrecyclerview.utils.SpaceType

class FlexboxItemDecoration(
    private val space: Int = 0,
    private val spaceType: Int = SpaceType.Center.type,
    private val includeFirst: Boolean = false,
    private val includeLast: Boolean = false,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.adapter == null) return
        val pos = parent.getChildAdapterPosition(view)
        val layoutManager = parent.layoutManager
        var startSpace = 0
        when (spaceType) {
            SpaceType.Center.type -> {
                startSpace = space / 2
            }

            SpaceType.Start.type -> {
                startSpace = space
            }

            SpaceType.End.type -> {
                startSpace = 0
            }
        }
        val allCount = parent.adapter!!.itemCount
        if (layoutManager is FlexboxLayoutManager) {

        }
    }
}