package com.wpf.app.quickrecyclerview.ability

import android.view.View
import androidx.annotation.IdRes
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.quickrecyclerview.ability.base.QuickItemAbility
import com.wpf.app.quickrecyclerview.data.QuickAbilityData
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder


fun <T : QuickAbilityData> click(
    @IdRes viewId: Int = 0,
    func: T.(View) -> Unit
): MutableList<QuickAbility> {
    return mutableListOf(
        object : QuickItemAbility<QuickAbilityData> {
            override fun getPrimeKey() = "click"

            override fun afterHolderOnCreateHolder(
                holder: QuickViewHolder<QuickItemData>,
                selfOnlyBase: QuickAbilityData
            ) {
                super.afterHolderOnCreateHolder(holder, selfOnlyBase)
                if (viewId == 0) {
                    holder.itemView.setOnClickListener {
                        func.invoke(selfOnlyBase.getViewRealData(holder) as T, it)
                    }
                } else {
                    holder.itemView.findViewById<View>(viewId).setOnClickListener {
                        func.invoke(selfOnlyBase.getViewRealData(holder) as T, it)
                    }
                }
            }
        })
}

fun <T : QuickAbilityData> longClick(
    @IdRes viewId: Int = 0,
    func: T.(View) -> Boolean
): MutableList<QuickAbility> {
    return mutableListOf(
        object : QuickItemAbility<QuickAbilityData> {
            override fun getPrimeKey() = "longClick"

            override fun afterHolderOnCreateHolder(
                holder: QuickViewHolder<QuickItemData>,
                selfOnlyBase: QuickAbilityData
            ) {
                super.afterHolderOnCreateHolder(holder, selfOnlyBase)
                if (viewId == 0) {
                    holder.itemView.setOnLongClickListener {
                        func.invoke(selfOnlyBase.getViewRealData(holder) as T, it)
                    }
                } else {
                    holder.itemView.findViewById<View>(viewId).setOnLongClickListener {
                        func.invoke(selfOnlyBase.getViewRealData(holder) as T, it)
                    }
                }
            }
        })
}