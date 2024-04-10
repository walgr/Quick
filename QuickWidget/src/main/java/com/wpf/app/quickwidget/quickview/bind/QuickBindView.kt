package com.wpf.app.quickwidget.quickview.bind

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.LayoutRes
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quickutil.run.RunOnContext
import com.wpf.app.quickwidget.quickview.QuickItemView

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickBindView @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @LayoutRes
    private val layoutId: Int = 0,
    layoutView: RunOnContext<View>? = null,
) : QuickItemView(mContext, attributeSet, defStyleAttr, layoutId, layoutView) {

    override fun onCreateViewHolder() {
        QuickBindWrap.bind(this)
    }

    override fun onBindViewHolder(position: Int) {

    }
}