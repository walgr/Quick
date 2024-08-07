package com.wpf.app.quickrecyclerview.ability.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.wpf.app.quickutil.ability.base.QuickAbility
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder

interface QuickItemAbility<T>: QuickAbility {

    /**
     * 在QuickAdapter的onCreateViewHolder初始化Holder之前调用
     * @param selfOnlyBase 假对象只能使用初始化相关参数
     */
    fun beforeAdapterCreateHolder(mParent: ViewGroup, selfOnlyBase: T) {

    }
    fun beforeHolderOnCreateHolder(holder: QuickViewHolder<QuickItemData>, selfOnlyBase: T) {

    }
    fun afterHolderOnCreateHolder(holder: QuickViewHolder<QuickItemData>, selfOnlyBase: T) {

    }

    /**
     * @param selfOnlyFirst 假对象只有首次有效
     */

    fun beforeOnCreateHolder(itemView: View, selfOnlyFirst: T) {

    }
    fun afterOnCreateHolder(itemView: View, selfOnlyFirst: T) {

    }
    /**
     * @param self 真对象能使用全部数据
     */
    fun beforeOnBindHolder(context: Context, self: T) {

    }
    fun afterOnBindHolder(context: Context, self: T) {

    }
}