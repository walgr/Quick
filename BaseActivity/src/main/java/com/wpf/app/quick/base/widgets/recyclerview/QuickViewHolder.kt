package com.wpf.app.quick.base.widgets.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.quick.base.helper.QuickBindHelper

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */
abstract class QuickViewHolder<T: QuickItemData>(
    mParent: ViewGroup,
    @LayoutRes open val layoutId: Int,
    open val dealBindView: Boolean = false
) : RecyclerView.ViewHolder(LayoutInflater.from(mParent.context).inflate(layoutId, mParent, false)) {

    var adapterListener: QuickAdapterListener<T>? = null

    @CallSuper
    open fun onCreateViewHolder(itemView: View) {
        if (dealBindView) {
            QuickBindHelper.bind(this)
        }
    }

    abstract fun onBindViewHolder(
        adapter: QuickAdapter,
        data: T,
        position: Int
    )

    fun getAdapterClickListener(): QuickAdapterListener<QuickItemData>? {
        return adapterListener as? QuickAdapterListener<QuickItemData>
    }
}