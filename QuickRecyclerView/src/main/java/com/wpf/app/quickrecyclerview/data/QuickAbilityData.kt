package com.wpf.app.quickrecyclerview.data

import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.QuickInflateViewAbility
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.ability.base.QuickItemAbility
import com.wpf.app.quickrecyclerview.ability.base.QuickViewTypeAbility
import com.wpf.app.quickrecyclerview.holder.QuickViewBindingHolder
import com.wpf.app.quickrecyclerview.holder.QuickViewHolder
import com.wpf.app.quickrecyclerview.widget.SwipeMenuLayout
import com.wpf.app.quickutil.helper.InitViewHelper
import com.wpf.app.quickutil.helper.activity
import com.wpf.app.quickutil.other.asTo
import com.wpf.app.quickutil.other.forceTo
import com.wpf.app.quickutil.run.runOnContextWithSelf

open class QuickAbilityData(
    autoSet: Boolean = false,                                                   //自动映射
    isSuspension: Boolean = false,                                              //View是否悬浮置顶
    private val abilityList: List<QuickAbility> = mutableListOf(),
) : QuickBindData(layoutViewInContext = runOnContextWithSelf { context ->
    val inflateAbility = abilityList.first { it is QuickInflateViewAbility }.forceTo<QuickInflateViewAbility>()
    var view = InitViewHelper.init(
        context,
        inflateAbility.layoutId(),
        inflateAbility.layoutView(),
        inflateAbility.layoutViewInContext()
    )
    view = inflateAbility.generateContentView(this.activity().forceTo(), view)
    inflateAbility.afterGenerateContentView(this.activity().forceTo(), view)
    view
}, autoSet = autoSet, isSuspension = isSuspension) {
    var isDealBinding = false

    @CallSuper
    fun initViewType(position: Int): Int {
        return abilityList.firstOrNull { it is QuickViewTypeAbility }?.forceTo<QuickViewTypeAbility>()?.initViewType(position) ?: viewType
    }

    @CallSuper
    override fun beforeAdapterCreateHolder(mParent: ViewGroup) {
        abilityList.filterIsInstance<QuickItemAbility<QuickAbilityData>>().forEach {
            it.beforeAdapterCreateHolder(mParent, this)
        }
    }

    @CallSuper
    override fun beforeHolderOnCreateHolder(holder: QuickViewHolder<QuickItemData>) {
        abilityList.filterIsInstance<QuickItemAbility<QuickAbilityData>>().forEach {
            it.beforeHolderOnCreateHolder(holder, this)
        }
    }

    @CallSuper
    override fun afterHolderOnCreateHolder(holder: QuickViewHolder<QuickItemData>) {
        abilityList.filterIsInstance<QuickItemAbility<QuickAbilityData>>().forEach {
            it.afterHolderOnCreateHolder(holder, this)
        }
    }

    @CallSuper
    override fun onCreateViewHolder(itemView: View) {
        abilityList.filterIsInstance<QuickItemAbility<QuickAbilityData>>().forEach {
            it.beforeOnCreateHolder(itemView, this)
        }
        super.onCreateViewHolder(itemView)
        abilityList.filterIsInstance<QuickItemAbility<QuickAbilityData>>().forEach {
            it.afterOnCreateHolder(itemView, this)
        }
    }

    @CallSuper
    override fun onBindViewHolder(
        adapter: QuickAdapter,
        viewHolder: QuickViewHolder<QuickBindData>,
        position: Int
    ) {
        abilityList.filterIsInstance<QuickItemAbility<QuickAbilityData>>().forEach {
            it.beforeOnBindHolder(viewHolder.itemView.context, this)
        }
        super.onBindViewHolder(adapter, viewHolder, position)
        abilityList.filterIsInstance<QuickItemAbility<QuickAbilityData>>().forEach {
            it.afterOnBindHolder(viewHolder.itemView.context, this)
        }
    }

    internal fun getViewRealData(viewHolder: RecyclerView.ViewHolder): QuickAbilityData? {
        return getAdapter()?.getData()?.get(viewHolder.bindingAdapterPosition) as? QuickAbilityData
    }

    fun <VB : ViewDataBinding> getViewBinding(): VB? {
        return getViewHolder()?.asTo<QuickViewBindingHolder<QuickItemData, VB>>()?.getViewBinding()
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