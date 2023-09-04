package com.wpf.app.quick.widgets.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickutil.bind.RunOnContext

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
        QuickBind.bind(this)
    }

    override fun onBindViewHolder(position: Int) {

    }
}