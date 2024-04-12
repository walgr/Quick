package com.wpf.app.quickrecyclerview.ability

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.Unique
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.ability.base.QuickItemAbility
import com.wpf.app.quickrecyclerview.data.QuickAbilityData


fun <VB : ViewDataBinding> binding(
    func: VB.() -> Unit
): MutableList<QuickAbility> {
    return bindWAdapter<VB> {
        func.invoke(this)
    }
}

fun <VB : ViewDataBinding> bindWAdapter(
    func: VB.(adapter: QuickAdapter) -> Unit
): MutableList<QuickAbility> {
    return bindWSelfWAdapter<VB, QuickAbilityData> { _, adapter ->
        func.invoke(this, adapter)
    }
}

fun <VB : ViewDataBinding, T : QuickAbilityData> bindWSelf(
    func: VB.(self: T) -> Unit
): MutableList<QuickAbility> {
    return bindWSelfWAdapter<VB, T> { self, _ ->
        func.invoke(this, self)
    }
}

fun <VB : ViewDataBinding, T : QuickAbilityData> bindWSelfWAdapter(
    func: VB.(self: T, adapter: QuickAdapter) -> Unit
): MutableList<QuickAbility> {
    return mutableListOf(
        object : QuickItemAbility<QuickAbilityData>, Unique {
            override fun getPrimeKey() = "binding"

            override fun afterOnBindHolder(context: Context, self: QuickAbilityData) {
                super.afterOnBindHolder(context, self)
                self.getViewBinding<VB>()?.let {
                    func.invoke(it, self as T, self.getAdapter()!!)
                }
            }
        })
}