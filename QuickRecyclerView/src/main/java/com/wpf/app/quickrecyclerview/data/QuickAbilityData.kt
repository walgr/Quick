package com.wpf.app.quickrecyclerview.data

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.ability.QuickAbility
import com.wpf.app.quickrecyclerview.ability.QuickContextAbility
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickutil.bind.RunOnContextWithSelf

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
    private val extraParameter: LinkedHashMap<String, Any> = linkedMapOf()
) : QuickBindData(layoutId, layoutViewInContext, isDealBinding, autoSet, isSuspension) {
    override fun onCreateHolder(context: Context) {
        abilityList.forEach {
            it.onCreateHolder(context, this)
        }
    }

    override fun onCreateViewHolder(itemView: View) {
        abilityList.forEach {
            it.beforeCreateHolder(itemView, getAdapter()!!)
        }
        super.onCreateViewHolder(itemView)
        abilityList.forEach {
            it.afterCreateHolder(itemView, getAdapter()!!)
        }
    }

    override fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        abilityList.forEach {
            it.beforeBindHolder(viewHolder.itemView.context, this)
        }
        super.onBindViewHolder(adapter, viewHolder, position)
        abilityList.forEach {
            it.afterBindHolder(viewHolder.itemView.context, this)
        }
    }
}

fun suspension(func: () -> Boolean): List<QuickAbility<QuickAbilityData>> {
    return mutableListOf(object : QuickAbility<QuickAbilityData> {
        override fun getPrimeKey() = "suspension"
        override fun onCreateHolder(context: Context, self: QuickAbilityData) {
            super.onCreateHolder(context, self)
            self.isSuspension = func.invoke()
        }
    })
}

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
            override fun onCreateHolder(context: Context, self: QuickAbilityData) {
                super.onCreateHolder(context, self)
                self.isDealBinding = true
            }

            override fun afterBindHolder(context: Context, self: QuickAbilityData) {
                super.afterBindHolder(context, self)
                self.getViewBinding<VB>()?.let {
                    func.invoke(it, self as T, self.getAdapter()!!)
                }
            }
        })
}

fun click(
    @IdRes viewId: Int = 0,
    func: (View) -> Unit
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
            override fun afterCreateHolder(itemView: View, quickAdapter: QuickAdapter) {
                super.afterCreateHolder(itemView, quickAdapter)
                if (viewId == 0) {
                    itemView.setOnClickListener {
                        func.invoke(quickAdapter.getRealTypeData<QuickAbilityData>()?.find { find ->
                            find.getView() == itemView
                        } as T, it)
                    }
                } else {
                    itemView.findViewById<View>(viewId).setOnClickListener {
                        func.invoke(quickAdapter.getRealTypeData<QuickAbilityData>()?.find { find ->
                            find.getView() == itemView
                        } as T, it)
                    }
                }
            }
        })
}