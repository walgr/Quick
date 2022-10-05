package com.wpf.app.quick.widgets.recyclerview.data

import android.content.Context
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quick.widgets.recyclerview.QuickAdapter
import com.wpf.app.quick.widgets.recyclerview.holder.QuickViewHolder
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

    @Transient
    private var mViewHolder: QuickViewHolder<QuickBindData>? = null
    @Transient
    private var mAdapter: QuickAdapter? = null
    private var dealBind = true
    @Transient
    private var mView: View? = null

    /**
     * TODO 列表中刷新会不执行onCreateViewHolder
     */
//    @Deprecated("有复用Bug")
    open fun onCreateViewHolder(itemView: View) {
        this.mView = itemView
        if (dealBind) {
            QuickBind.bind(this)
        }
    }

    open fun getContext(): Context? {
        return mView?.context
    }

    @CallSuper
    open fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        mAdapter = adapter
        mViewHolder = viewHolder
        mView = viewHolder.itemView
        if (dealBind) {
            dealInPlugins(this, null, QuickBind.bindPlugin)
        }
    }

    fun noBind() {
        this.dealBind = false
    }

    open fun getAdapter(): QuickAdapter? {
        return mAdapter
    }

    open fun getViewHolder(): QuickViewHolder<QuickBindData>? {
        return mViewHolder
    }

    override fun getView(): View? {
        return mView
    }

//    override fun initViewType() {
//        if (viewType == 0) {
//            viewType = abs(this.hashCode()) + Random.nextInt(1000)
//        }
//    }
}