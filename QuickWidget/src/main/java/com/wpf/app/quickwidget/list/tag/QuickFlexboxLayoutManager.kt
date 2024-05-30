package com.wpf.app.quickwidget.list.tag

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager

class QuickFlexboxLayoutManager(
    context: Context,
) : FlexboxLayoutManager(context) {
    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?): RecyclerView.LayoutParams {
        return if (lp is ViewGroup.MarginLayoutParams) {
            LayoutParams(lp as ViewGroup.MarginLayoutParams?)
        } else {
            LayoutParams(lp)
        }
    }
}