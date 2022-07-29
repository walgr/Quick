package com.wpf.app.quick.widgets.recyclerview

import android.content.Context
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickbind.QuickBind.dealInPlugins
import com.wpf.app.quickbind.interfaces.Bind

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickBindData(
    @LayoutRes open val layoutId: Int,
) : QuickItemData(), Bind {

    private var mViewHolder: QuickViewHolder<QuickBindData>? = null
    private lateinit var mAdapter: QuickAdapter
    private var dealBind = true
    private lateinit var mView: View

    open fun onCreateViewHolder(itemView: View) {
        this.mView = itemView
        if (dealBind) {
            QuickBind.bind(this)
        }
    }

    open fun getContext(): Context {
        return mView.context
    }

    @CallSuper
    open fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        mAdapter = adapter
        mViewHolder = viewHolder
        if (dealBind) {
            dealInPlugins(this, null, QuickBind.bindPlugin)
        }
    }

    fun noBind() {
        this.dealBind = false
    }

    fun getAdapter(): QuickAdapter {
        return mAdapter
    }

    fun getViewHolder(): QuickViewHolder<QuickBindData>? {
        return mViewHolder
    }

    override fun getView(): View {
        return mView
    }
}