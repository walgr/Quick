package com.wpf.app.quickwidget.wheel

import android.graphics.Color
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.CallSuper
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.helper.dp
import com.wpf.app.quickutil.helper.matchWrapMarginLayoutParams
import com.wpf.app.quickutil.helper.reset
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.run.RunOnContextWithSelf
import com.wpf.app.quickutil.run.runOnContextWithSelf
import com.wpf.app.quickwidget.selectview.QuickSelectAdapter
import com.wpf.app.quickwidget.selectview.data.QuickSelectData

open class WheelItemData(
    id: String? = null,
    name: String? = null,
    layoutId: Int = 0,
    layoutViewCreate: RunOnContextWithSelf<ViewGroup, View>? = null,
): QuickSelectData(
    layoutId = layoutId,
    layoutViewCreate = layoutViewCreate ?: runOnContextWithSelf {
        TextView(context).apply {
            layoutParams = matchWrapMarginLayoutParams().reset(height = 44.dp)
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.MARQUEE
            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(Color.BLACK)
        }
    },
    id = id,
    name = name
) {

    @Transient
    private var itemView: View? = null
    @CallSuper
    override fun onBindViewHolder(
        adapter: QuickSelectAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int,
    ) {
        super.onBindViewHolder(adapter, viewHolder, position)
        itemView = viewHolder.itemView
        val name = itemView!!.forceTo<TextView>()
        name.text = this.name
    }

    open fun showCenterItem() {
        itemView?.isSelected = true
    }

    open fun showOtherItem() {
        itemView?.isSelected = false
    }
}