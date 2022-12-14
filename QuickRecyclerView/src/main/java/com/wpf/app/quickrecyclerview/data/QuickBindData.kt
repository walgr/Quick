package com.wpf.app.quickrecyclerview.data

import android.content.Context
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.bind.Bind
import com.wpf.app.quickutil.bind.QuickBindWrap

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickBindData @JvmOverloads constructor(
    @LayoutRes open val layoutId: Int = 0,
    @Transient open val layoutView: View? = null,
    isSuspension: Boolean = false,         //View是否悬浮置顶
) : QuickItemData(isSuspension = isSuspension), Bind {

    @Transient
    private var mViewHolder: QuickViewHolder<QuickBindData>? = null

    @Transient
    private var mAdapter: QuickAdapter? = null
    private var dealBind = true

    @Transient
    private var mView: View? = null

    @Transient
    private var isFirstLoad = true

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
        if (isFirstLoad) {
            onCreateViewHolder(itemView = viewHolder.itemView)
        } else {
            if (dealBind) {
                QuickBind.dealInPlugins(this, null, QuickBind.bindPlugin)
            }
        }
        isFirstLoad = false
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

    open fun getDataPos(): Int {
        return getAdapter()?.getData()?.indexOf(this) ?: 0
    }

    open fun getViewPos(): Int {
        return getViewHolder()?.bindingAdapterPosition ?: 0
    }

    override fun getView(): View? {
        return mView
    }
}