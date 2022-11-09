package com.wpf.app.quick.widgets.quickview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.QuickBind.dealInPlugins
import com.wpf.app.quickutil.bind.Bind

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickBindGroup<T : ViewGroup> @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    @LayoutRes
    private val layoutId: Int = 0,
    private var dealBind: Boolean = true
) : QuickItemGroup<T>(mContext, attributeSet, defStyleAttr, layoutId), Bind {

    private var isLoadFirst = true
    override fun onCreateViewHolder() {
        if (dealBind) {
            QuickBind.bind(this)
        }
    }

    @CallSuper
    override fun onBindViewHolder(position: Int) {
        if (isLoadFirst) {
            onCreateViewHolder()
            isLoadFirst = false
        }
        if (dealBind) {
            dealInPlugins(this, null, QuickBind.bindPlugin)
        }
    }

    fun noBind() {
        this.dealBind = false
    }
}