package com.wpf.app.quickrecyclerview.data

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.base.bind.Bind
import com.wpf.app.base.bind.QuickBindWrap
import com.wpf.app.quickbind.utils.DataAutoSet2ViewUtils
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.other.nullDefault
import com.wpf.app.quickutil.run.RunOnContextWithSelf
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/7/13.
 * 绑定数据到xml的Item
 */
open class QuickBindData @JvmOverloads constructor(
    layoutId: Int = 0,
    layoutViewCreate: RunOnContextWithSelf<ViewGroup, View>? = null,
    open var autoSet: Boolean = false,              //自动映射
    isSuspension: Boolean = false,                   //View是否悬浮置顶
) : QuickViewData(
    layoutId = layoutId,
    layoutViewInViewGroup = layoutViewCreate,
    isSuspension = isSuspension
), Bind, Serializable {

    @Transient
    private var mViewHolder: QuickViewHolder<QuickBindData>? = null

    @Transient
    protected var mAdapter: QuickAdapter? = null

    @Transient
    private var mView: View? = null

    @Transient
    private var isFirstLoad = true

    @CallSuper
    open fun onCreateViewHolder(itemView: View) {
        this.mView = itemView
        QuickBindWrap.bind(this)
    }

    open fun getContext(): Context? {
        return mView?.context
    }

    @CallSuper
    open fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int,
    ) {
        mAdapter = adapter
        mViewHolder = viewHolder
        mView = viewHolder.itemView
        if (isFirstLoad) {
            onCreateViewHolder(itemView = viewHolder.itemView)
        } else {
            QuickBindWrap.dealInPlugins(this, null, QuickBindWrap.getBindPlugin())
        }
        if (autoSet) {
            mView?.let {
                DataAutoSet2ViewUtils.autoSet(this, it)
            }
        }
        isFirstLoad = false
    }

    open fun getAdapter(): QuickAdapter? {
        return mAdapter
    }

    open fun setAdapter(adapter: QuickAdapter) {
        this.mAdapter = adapter
    }

    open fun getViewHolder(): QuickViewHolder<QuickBindData>? {
        return mViewHolder
    }

    fun getRecyclerView(): RecyclerView? {
        return getAdapter()?.getRecyclerView()
    }

    open fun getViewPos(): Int {
        return getViewHolder()?.bindingAdapterPosition.nullDefault(0)
    }

    override fun getView(): View? {
        return mView
    }
}