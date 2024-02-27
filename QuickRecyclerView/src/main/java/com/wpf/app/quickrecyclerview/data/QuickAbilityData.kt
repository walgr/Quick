package com.wpf.app.quickrecyclerview.data

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
    @CallSuper
    override fun beforeAdapterCreateHolder(mParent: ViewGroup) {
        abilityList.forEach {
            it.beforeAdapterCreateHolder(mParent, this)
        }
    }

    @CallSuper
    override fun beforeHolderOnCreateHolder(holder: QuickViewHolder<QuickItemData>) {
        abilityList.forEach {
            it.beforeHolderOnCreateHolder(holder, this)
        }
    }

    @CallSuper
    override fun afterHolderOnCreateHolder(holder: QuickViewHolder<QuickItemData>) {
        abilityList.forEach {
            it.afterHolderOnCreateHolder(holder, this)
        }
    }

    @CallSuper
    override fun onCreateViewHolder(itemView: View) {
        abilityList.forEach {
            it.beforeOnCreateHolder(itemView, this)
        }
        super.onCreateViewHolder(itemView)
        abilityList.forEach {
            it.afterOnCreateHolder(itemView, this)
        }
    }

    @CallSuper
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

    internal fun getViewRealData(viewHolder: RecyclerView.ViewHolder): QuickAbilityData? {
        return getAdapter()?.getData()?.get(viewHolder.bindingAdapterPosition) as? QuickAbilityData
    }

    fun closeSwipeMenu() {
        getView()?.asTo<SwipeMenuLayout>()?.smoothClose()
    }

    internal fun cleanData() {
        getAdapter()?.extraParameter?.clear()
    }

    internal fun putData(key: String, value: Any) {
        getAdapter()?.extraParameter?.put(key, value)
    }

    internal fun <T : Any> getData(key: String, defaultValue: T) =
        getAdapter()?.extraParameter?.get(key) as? T ?: defaultValue

    internal fun remove(key: String) {
        getAdapter()?.extraParameter?.remove(key)
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
            override fun beforeAdapterCreateHolder(
                mParent: ViewGroup,
                selfOnlyBase: QuickAbilityData
            ) {
                super.beforeAdapterCreateHolder(mParent, selfOnlyBase)
                selfOnlyBase.isDealBinding = true
            }

            override fun afterOnBindHolder(context: Context, self: QuickAbilityData) {
                super.afterOnBindHolder(context, self)
                self.getViewBinding<VB>()?.let {
                    func.invoke(it, self as T, self.getAdapter()!!)
                }
            }
        })
}

fun <T : QuickAbilityData> click(
    @IdRes viewId: Int = 0,
    func: T.(View) -> Unit
): List<QuickContextAbility<QuickAbilityData>> {
    return mutableListOf(
        object : QuickContextAbility<QuickAbilityData> {
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
): List<QuickContextAbility<QuickAbilityData>> {
    return mutableListOf(
        object : QuickContextAbility<QuickAbilityData> {
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

fun <VB : ViewDataBinding, T : QuickAbilityData> bindSwipeMenu(
    @LayoutRes layoutId: Int,
    canSwipe: T.() -> Boolean = { true },
    func: VB.(self: T, swipeLayout: SwipeMenuLayout) -> Unit
): List<QuickContextAbility<QuickAbilityData>> {
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
            override fun beforeAdapterCreateHolder(
                mParent: ViewGroup,
                selfOnlyBase: QuickAbilityData
            ) {
                super.beforeAdapterCreateHolder(mParent, selfOnlyBase)
                val oldLayoutViewInGroup = selfOnlyBase.layoutViewInViewGroup
                if (getPrimeKey() == oldLayoutViewInGroup?.primeKey()) return
                selfOnlyBase.layoutViewInViewGroup =
                    runOnContextWithSelf(getPrimeKey()) { _, parent ->
                        val itemView =
                            oldLayoutViewInGroup?.run(parent.context, parent)
                                ?: selfOnlyBase.layoutId.toView(
                                    parent.context,
                                    parent
                                )
                        val swipeView = layoutId.toView(parent.context)
                        SwipeMenuLayout(parent.context).apply {
                            addView(itemView)
                            addView(swipeView, wrapMatchMarginLayoutParams)
                            layoutParams = itemView.layoutParams
                        }
                    }
            }

            override fun beforeHolderOnCreateHolder(
                holder: QuickViewHolder<QuickItemData>,
                selfOnlyBase: QuickAbilityData
            ) {
                super.beforeHolderOnCreateHolder(holder, selfOnlyBase)
                if (selfOnlyBase.isDealBinding) {
                    holder.to<QuickViewBindingHolder<QuickAbilityData, ViewDataBinding>>().apply {
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

fun <T : QuickAbilityData> swap(
    canSwap: T.() -> Boolean = { true },
): List<QuickContextAbility<QuickAbilityData>> {
    return mutableListOf(
        object : QuickContextAbility<QuickAbilityData> {
            override fun getPrimeKey() = "swap"

            override fun beforeHolderOnCreateHolder(
                holder: QuickViewHolder<QuickItemData>,
                selfOnlyBase: QuickAbilityData
            ) {
                super.beforeHolderOnCreateHolder(holder, selfOnlyBase)
                val isAddSwapKey = "isAddSwap"
                val isAddSwap: Boolean = selfOnlyBase.getData(isAddSwapKey, false)
                if (isAddSwap) return
                selfOnlyBase.putData(isAddSwapKey, true)
                ItemTouchHelper(object : ItemTouchHelper.Callback() {
                    override fun getMovementFlags(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder
                    ): Int {
                        val adapter =
                            recyclerView.adapter.asTo<QuickAdapter>() ?: return makeMovementFlags(
                                0,
                                0
                            )
                        val firstData = adapter.getData()?.first { it is QuickAbilityData }
                            ?.asTo<QuickAbilityData>() ?: return makeMovementFlags(0, 0)
                        val dragFlags =
                            if (canSwap.invoke(firstData.getViewRealData(viewHolder) as T)) (ItemTouchHelper.UP or ItemTouchHelper.DOWN) else 0
                        return makeMovementFlags(dragFlags, 0)
                    }

                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        recyclerView.adapter.asTo<QuickAdapter>()?.onMove(
                            viewHolder.bindingAdapterPosition,
                            target.bindingAdapterPosition
                        )
                        return true
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    }

                }).apply {
                    val oldLayoutManager = selfOnlyBase.getRecyclerView()?.layoutManager
                    selfOnlyBase.getRecyclerView()?.layoutManager = null
                    attachToRecyclerView(selfOnlyBase.getRecyclerView())
                    selfOnlyBase.getRecyclerView()?.layoutManager = oldLayoutManager
                }
            }
        }
    )
}