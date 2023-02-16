package com.wpf.app.quick.widgets.quickview.inxml

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import com.wpf.app.quick.widgets.quickview.AddToParentGroup

class LoadSp(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AddToParentGroup<ViewGroup> (
    mContext, attributeSet, defStyleAttr
) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount == 1 && getChildAt(0) is TextView) {
            //只能套在基于TextView的View上

        }
    }
}