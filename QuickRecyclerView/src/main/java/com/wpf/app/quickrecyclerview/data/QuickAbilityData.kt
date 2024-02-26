package com.wpf.app.quickrecyclerview.data

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.ability.QuickContextAbility
import com.wpf.app.quickrecyclerview.holder.QuickViewBindingHolder
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickrecyclerview.widget.SwipeMenuLayout
import com.wpf.app.quickutil.bind.RunOnContextWithSelf
import com.wpf.app.quickutil.bind.runOnContextWithSelf
import com.wpf.app.quickutil.helper.toView
import com.wpf.app.quickutil.helper.wrapMatchMarginLayoutParams
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.to

fun <T : QuickContextAbility<*>> List<T>.with(others: List<T>): List<T> {
    val newList = mutableListOf<T>()
    newList.addAll(this)
    newList.addAll(others)
    return newList
}

fun <T : QuickContextAbility<*>> List<T>.with(other: T): List<T> {
    val newList = mutableListOf<T>()
    newList.addAll(this)
    newList.add(other)
    return newList
}

open class QuickAbilityData(
    layoutId: Int = 0,
    layoutViewInContext: RunOnContextWithSelf<ViewGroup, View>? = null,
    isDealBinding: Boolean = false,                                             //是否处理DataBinding
    autoSet: Boolean = false,                                                   //自动映射
    isSuspension: Boolean = false,                                              //View是否悬浮置顶
    @Transient val abilityList: List<QuickContextAbility<QuickAbilityData>> = mutableListOf(),
) : QuickBindData(layoutId, layoutViewInContext, isDealBinding, autoSet, isSuspension) {
    private val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()
    override fun beforeCreateHolder(mParent: ViewGroup) {
        abilityList.forEach {
            it.beforeCreateHolder(mParent, this)
        }
    }

    override fun beforeHolderOnCreateHolder(holder: QuickViewHolder<QuickItemData>) {
        abilityList.forEach {
            it.beforeHolderOnCreateHolder(holder, this)
        }
    }

    override fun afterHolderOnCreateHolder(holder: QuickViewHolder<QuickItemData>) {
        abilityList.forEach {
            it.afterHolderOnCreateHolder(holder, this)
        }
    }

    override fun onCreateViewHolder(itemView: View) {
        abilityList.forEach {
            it.beforeOnCreateHolder(itemView, this)
        }
        super.onCreateViewHolder(itemView)
        abilityList.forEach {
            it.afterOnCreateHolder(itemView, this)
        }
    }

    override fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        abilityList.forEach {
            it.beforeOnBindHolder(viewHolder.itemView.context, this)
        }
        super.onBindViewHolder(adapter, viewHolder, position)
        abilityList.forEach {
            it.afterOnBindHolder(viewHolder.itemView.context, this)
        }
    }

    private fun getRealData(itemView: View): QuickAbilityData {
        return getAdapter()?.getRealTypeData<QuickAbilityData>()?.find {
            it.getView() == itemView
        }!!
    }
}

//fun suspension(func: () -> Boolean): List<QuickAbility<QuickAbilityData>> {
//    return suspension<QuickAbilityData> { func.invoke() }
//}
//
//fun <T : QuickAbilityData> suspension(func: T.() -> Boolean): List<QuickAbility<QuickAbilityData>> {
//    return mutableListOf(object : QuickAbility<QuickAbilityData> {
//        override fun getPrimeKey() = "suspension"
//        override fun beforeBindHolder(context: Context, self: QuickAbilityData) {
//            super.beforeBindHolder(context, self)
//            self.isSuspension = func.invoke(self as T)
//        }
//    })
//}

fun <VB : ViewDataBinding> binding(
    func: VB.() -> Unit
): List<QuickContextAbility<QuickAbilityData>> {
    return bindWAdapter<VB> {
        func.invoke(this)
    }
}

fun <VB : ViewDataBinding> bindWAdapter(
    func: VB.(adapter: QuickAdapter) -> Unit
): List<QuickContextAbility<QuickAbilityData>> {
    return bindWSelfWAdapter<VB, QuickAbilityData> { _, adapter ->
        func.invoke(this, adapter)
    }
}

fun <VB : ViewDataBinding, T : QuickAbilityData> bindWSelf(
    func: VB.(self: T) -> Unit
): List<QuickContextAbility<QuickAbilityData>> {
    return bindWSelfWAdapter<VB, T> { self, _ ->
        func.invoke(this, self)
    }
}

fun <VB : ViewDataBinding, T : QuickAbilityData> bindWSelfWAdapter(
    func: VB.(self: T, adapter: QuickAdapter) -> Unit
): List<QuickContextAbility<QuickAbilityData>> {
    return mutableListOf(
        object : QuickContextAbility<QuickAbilityData> {
            override fun getPrimeKey() = "binding"
            override fun beforeCreateHolder(mParent: ViewGroup, self: QuickAbilityData) {
                super.beforeCreateHolder(mParent, self)
                self.isDealBinding = true
            }

            override fun afterOnBindHolder(context: Context, self: QuickAbilityData) {
                super.afterOnBindHolder(context, self)
                self.getViewBinding<VB>()?.let {
                    func.invoke(it, self as T, self.getAdapter()!!)
                }
            }
        })
}

fun click(
    @IdRes viewId: Int = 0,
    func: View.() -> Unit
): List<QuickContextAbility<QuickAbilityData>> {
    return clickWSelf<QuickAbilityData>(viewId) {
        func.invoke(it)
    }
}

fun <T : QuickAbilityData> clickWSelf(
    @IdRes viewId: Int = 0,
    func: T.(View) -> Unit
): List<QuickContextAbility<QuickAbilityData>> {
    return mutableListOf(
        object : QuickContextAbility<QuickAbilityData> {
            override fun getPrimeKey() = "click"
            override fun afterOnCreateHolder(itemView: View, self: QuickAbilityData) {
                super.afterOnCreateHolder(itemView, self)
                if (viewId == 0) {
                    itemView.setOnClickListener {
                        func.invoke(
                            self.getAdapter()?.getRealTypeData<QuickAbilityData>()?.find { find ->
                                find.getView() == itemView
                            } as T, it)
                    }
                } else {
                    itemView.findViewById<View>(viewId).setOnClickListener {
                        func.invoke(
                            self.getAdapter()?.getRealTypeData<QuickAbilityData>()?.find { find ->
                                find.getView() == itemView
                            } as T, it)
                    }
                }
            }
        })
}

fun <T : QuickAbilityData, VB : ViewDataBinding> bindingSwipeMenu(
    @LayoutRes layoutId: Int,
    canSwipe: T.() -> Boolean = { true },
    func: VB.(self: T, swipeLayout: SwipeMenuLayout) -> Unit
): List<QuickContextAbility<QuickAbilityData>> {
    return swipeMenu(layoutId, canSwipe, func = object : (T, View, SwipeMenuLayout) -> Unit {
        override fun invoke(p1: T, swipeView: View, swipeLayout: SwipeMenuLayout) {
            func.invoke(DataBindingUtil.bind(swipeView)!!, p1, swipeLayout)
        }
    })
}

/**
 * 侧滑
 */
fun <T : QuickAbilityData> swipeMenu(
    @LayoutRes layoutId: Int,
    canSwipe: T.() -> Boolean = { true },
    func: T.(swipeView: View, swipeLayout: SwipeMenuLayout) -> Unit
): List<QuickContextAbility<QuickAbilityData>> {
    return mutableListOf(
        object : QuickContextAbility<QuickAbilityData> {
            override fun getPrimeKey() = "swipe"

            override fun beforeCreateHolder(mParent: ViewGroup, self: QuickAbilityData) {
                super.beforeCreateHolder(mParent, self)
                val canSwipeValue = canSwipe.invoke(self as T)
                if (!canSwipeValue) return
                val oldLayoutViewInContext = self.layoutViewInContext
                if (getPrimeKey() == oldLayoutViewInContext?.primeKey()) return
                self.layoutViewInContext = runOnContextWithSelf(getPrimeKey()) { _, _ ->
                    val itemView =
                        oldLayoutViewInContext?.run(mParent.context, mParent)
                            ?: self.layoutId.toView(
                                mParent.context,
                                mParent
                            )
                    val swipeView = layoutId.toView(mParent.context)
                    SwipeMenuLayout(mParent.context).apply {
                        addView(itemView)
                        addView(swipeView, wrapMatchMarginLayoutParams)
                        layoutParams = itemView.layoutParams
                    }
                }
            }

            override fun beforeHolderOnCreateHolder(
                holder: QuickViewHolder<QuickItemData>,
                self: QuickAbilityData
            ) {
                super.beforeHolderOnCreateHolder(holder, self)
                if (self.isDealBinding) {
                    holder.to<QuickViewBindingHolder<QuickAbilityData, ViewDataBinding>>().apply {
                        holder.itemView.asTo<SwipeMenuLayout>()?.contentView()?.let {
                            this.mViewBinding = DataBindingUtil.bind(it)
                        }

                    }
                }
            }

            override fun afterOnCreateHolder(itemView: View, self: QuickAbilityData) {
                super.afterOnCreateHolder(itemView, self)
                val swipeLayout = itemView.to<SwipeMenuLayout>()
                func.invoke(self as T, swipeLayout.getChildAt(1), swipeLayout)
            }
        })
}