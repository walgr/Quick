package com.wpf.app.quick.widgets.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.interfaces.RunOnContext

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
    private var dealBind: Boolean = true
) : QuickItemView(mContext, attributeSet, defStyleAttr, layoutId, layoutView) {

    override fun onCreateViewHolder() {
        if (dealBind) {
            QuickBind.bind(this)
        }
    }

    @CallSuper
    override fun onBindViewHolder(position: Int) {
//        if (dealBind) {
//            QuickBind.dealInPlugins(this, null, QuickBind.bindPlugin)
//        }
    }

    fun noBind() {
        this.dealBind = false
    }
}