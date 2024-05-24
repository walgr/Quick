package com.wpf.app.quickwidget.wheel

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class WheelLayoutManager(
    context: Context
): LinearLayoutManager(context) {

//    override fun scrollVerticallyBy(
//        dy: Int,
//        recycler: RecyclerView.Recycler?,
//        state: RecyclerView.State?
//    ): Int {
//        val scrolled = super.scrollVerticallyBy(dy, recycler, state)
//        val midpoint = height / 2
//        val d0 = 0f
//        val d1 = 0.5f * midpoint
//        val s0 = 1f
//        val s1 = 1f - 0.5f
//        repeat(childCount) {
//            val child = getChildAt(it)!!
//            val childMidPoint = (getDecoratedBottom(child) + getDecoratedTop(child)) / 2f
//            val d = min(d1, abs(midpoint - childMidPoint))
//            val scale = s0 + (s1 -s0) * (d - d0) / (d1 - d0)
//            child.rotationX = (it - childCount / 2) * -(360f / 9 / 4)
//        }
//        return scrolled
//    }
}