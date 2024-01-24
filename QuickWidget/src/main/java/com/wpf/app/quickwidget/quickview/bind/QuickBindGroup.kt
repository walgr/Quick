package com.wpf.app.quickwidget.quickview.bind

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quickutil.bind.Bind
import com.wpf.app.quickutil.bind.QuickBindWrap
import com.wpf.app.quickwidget.quickview.QuickItemGroup

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickBindGroup<T : ViewGroup> @JvmOverloads constructor(
    mContext: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    addToParent: Boolean = true,
    @LayoutRes private val layoutId: Int = 0,
    private var dealBind: Boolean = true
) : QuickItemGroup<T>(
    mContext,
    attributeSet,
    defStyleAttr,
    addToParent = addToParent,
    layoutId = layoutId
), Bind {

    private var isLoadFirst = true
    override fun onCreateViewHolder() {
        if (dealBind) {
            QuickBindWrap.bind(this)
        }
    }

    @CallSuper
    override fun onBindViewHolder(position: Int) {
        if (isLoadFirst) {
            onCreateViewHolder()
            isLoadFirst = false
        }
        if (dealBind) {
            QuickBindWrap.dealInPlugins(this, null, QuickBindWrap.getBindPlugin())
        }
    }

    fun noBind() {
        this.dealBind = false
    }
}