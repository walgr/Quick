package com.wpf.app.quick.base.widgets.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */
abstract class CommonViewHolder<T: CommonItemData>(
    mParent: ViewGroup,
    @LayoutRes open val layoutId: Int
) : RecyclerView.ViewHolder(LayoutInflater.from(mParent.context).inflate(layoutId, mParent, false)) {

    abstract fun bindViewBinding(view: View)

    abstract fun onBindViewHolder(
        adapter: CommonAdapter,
        data: T,
        position: Int
    )
}