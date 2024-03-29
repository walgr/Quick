package com.wpf.app.quickrecyclerview.holder

import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.data.QuickBindData
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.listeners.QuickAdapterListener
import com.wpf.app.quickbind.QuickBind
import com.wpf.app.quickutil.bind.RunOnContextWithSelf
import com.wpf.app.quickutil.helper.toView

/**
 * Created by 王朋飞 on 2022/7/13.
 *
 */
open class QuickViewHolder<T : QuickItemData> @JvmOverloads constructor(
    mParent: ViewGroup,
    @LayoutRes layoutId: Int = 0,
    layoutViewInContext: RunOnContextWithSelf<ViewGroup, View>? = null,
    open var dealBindView: Boolean = false,
    open var autoClick: Boolean = false
) : RecyclerView.ViewHolder(
    layoutViewInContext?.run(mParent.context, mParent) ?: layoutId.toView(
        mParent.context,
        mParent,
    )
) {

    var mQuickAdapter: QuickAdapter? = null
    var itemPosition: Int = -1

    fun getQuickAdapter(): QuickAdapter? {
        return mQuickAdapter
    }

    @CallSuper
    open fun onCreateViewHolder(itemView: View) {
        if (dealBindView) {
            QuickBind.bind(this)
        }
        if (autoClick) {
            itemView.setOnClickListener { v: View ->
                (mQuickAdapter?.getQuickAdapterListener() as QuickAdapterListener<T>).onItemClick(
                    v, getViewData(), bindingAdapterPosition
                )
            }
        }
    }

    @CallSuper
    open fun onBindViewHolder(adapter: QuickAdapter, data: T?, position: Int) {
        if (data is QuickBindData) {
            data.onBindViewHolder(
                adapter, (this as QuickViewHolder<QuickBindData>), position
            )
        }
    }

    open fun getAdapterClickListener(): QuickAdapterListener<QuickItemData>? {
        return this.mQuickAdapter?.getQuickAdapterListener()
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
        return mQuickAdapter?.getData()?.getOrNull(bindingAdapterPosition) as? T
    }
}