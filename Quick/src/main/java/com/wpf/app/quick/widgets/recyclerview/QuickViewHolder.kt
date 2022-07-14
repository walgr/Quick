package com.wpf.app.quick.widgets.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickbind.QuickBind

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickViewHolder<T : QuickItemData> @JvmOverloads constructor(
    open val mParent: ViewGroup,
    @LayoutRes open val layoutId: Int,
    open var dealBindView: Boolean = false,
    open var autoClick: Boolean = false
) : RecyclerView.ViewHolder(LayoutInflater.from(mParent.context).inflate(layoutId, mParent, false)) {

    private lateinit var mQuickAdapter: QuickAdapter

    fun getQuickAdapter(): QuickAdapter {
        return mQuickAdapter
    }

    @CallSuper
    open fun onCreateViewHolder(itemView: View) {
        if (dealBindView) {
            QuickBind.bind(this)
        }
        if (autoClick) {
            itemView.setOnClickListener { v: View? ->
                (mQuickAdapter?.getQuickAdapterListener() as? QuickAdapterListener<T>)?.onItemClick(
                    v, getViewData(), bindingAdapterPosition
                )
            }
        }
    }

    @CallSuper
    open fun onBindViewHolder(adapter: QuickAdapter, data: T?, position: Int) {
        this.mQuickAdapter = adapter
        if (data is QuickBindData) {
            data.onBindViewHolder(
                adapter, (this as QuickViewHolder<out QuickBindData>), position
            )
        }
    }

    open fun getAdapterClickListener(): QuickAdapterListener<QuickItemData>? {
        return this.mQuickAdapter?.getQuickAdapterListener() as? QuickAdapterListener<QuickItemData>?
    }

    open fun itemViewClick(clickView: View?) {
        getAdapterClickListener()?.onItemClick(clickView, getViewData(), bindingAdapterPosition)
    }

    open fun isAutoClick(): Boolean {
        return autoClick
    }

    open fun getItemView(): View? {
        return itemView
    }

    open fun getViewData(): T? {
        return mQuickAdapter?.getData()?.get(bindingAdapterPosition) as? T
    }
}