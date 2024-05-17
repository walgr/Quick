package com.wpf.app.quickrecyclerview.ability

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.Unique
import com.wpf.app.quickrecyclerview.ability.base.QuickItemAbility
import com.wpf.app.quickrecyclerview.data.QuickAbilityData
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.holder.QuickViewBindingHolder
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickrecyclerview.widget.SwipeMenuLayout
import com.wpf.app.quickutil.helper.toView
import com.wpf.app.quickutil.helper.wrapMatchMarginLayoutParams
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.run.runOnContextWithSelf
import com.wpf.app.quickutil.widget.wishLayoutParams


/**
 * 侧滑
 */
fun <T : QuickAbilityData> swipeMenu(
    @LayoutRes layoutId: Int,
    canSwipe: T.() -> Boolean = { true },
    func: T.(swipeView: View, swipeLayout: SwipeMenuLayout) -> Unit,
): MutableList<QuickAbility> {
    return mutableListOf(
        object : QuickItemAbility<QuickAbilityData>, Unique {
            override fun getPrimeKey() = "swipe"
            override fun beforeAdapterCreateHolder(
                mParent: ViewGroup,
                selfOnlyBase: QuickAbilityData,
            ) {
                super.beforeAdapterCreateHolder(mParent, selfOnlyBase)
                val oldLayoutViewInGroup = selfOnlyBase.layoutViewInViewGroup
                if (getPrimeKey() == oldLayoutViewInGroup?.primeKey()) return
                selfOnlyBase.layoutViewInViewGroup =
                    runOnContextWithSelf(getPrimeKey()) { context ->
                        val itemView =
                            oldLayoutViewInGroup?.run(context, this)
                                ?: selfOnlyBase.layoutId.toView(
                                    context,
                                    this
                                )
                        val swipeView = layoutId.toView(context)
                        SwipeMenuLayout(context).apply {
                            addView(itemView, itemView.wishLayoutParams<MarginLayoutParams>())
                            addView(swipeView, wrapMatchMarginLayoutParams())
                        }
                    }
            }

            override fun beforeHolderOnCreateHolder(
                holder: QuickViewHolder<QuickItemData>,
                selfOnlyBase: QuickAbilityData,
            ) {
                super.beforeHolderOnCreateHolder(holder, selfOnlyBase)
                if (selfOnlyBase.isDealBinding) {
                    holder.forceTo<QuickViewBindingHolder<QuickAbilityData, ViewDataBinding>>()
                        .apply {
                            holder.itemView.asTo<SwipeMenuLayout>()?.contentView()?.let {
                                this.mViewBinding = DataBindingUtil.bind(it)
                            }

                        }
                }
            }

            override fun afterOnBindHolder(context: Context, self: QuickAbilityData) {
                super.afterOnBindHolder(context, self)
                self.getViewHolder()?.getItemView()?.asTo<SwipeMenuLayout>()?.let {
                    it.isSwipeEnable = canSwipe.invoke(self as T)
                    func.invoke(self, it.getChildAt(1), it)
                }
            }
        })
}

fun <VB : ViewDataBinding, T : QuickAbilityData> bindSwipeMenu(
    @LayoutRes layoutId: Int,
    canSwipe: T.() -> Boolean = { true },
    func: VB.(self: T, swipeLayout: SwipeMenuLayout) -> Unit,
): MutableList<QuickAbility> {
    return swipeMenu(layoutId, canSwipe, func = object : (T, View, SwipeMenuLayout) -> Unit {
        override fun invoke(p1: T, swipeView: View, swipeLayout: SwipeMenuLayout) {
            func.invoke(
                DataBindingUtil.getBinding(swipeView) ?: DataBindingUtil.bind(swipeView)!!,
                p1,
                swipeLayout
            )
        }
    })
}