package com.wpf.app.quickrecyclerview.ability

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.wpf.app.base.ability.base.QuickAbility
import com.wpf.app.base.ability.base.Unique
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.ability.base.QuickItemAbility
import com.wpf.app.quickrecyclerview.data.QuickAbilityData

inline fun <reified VB : ViewDataBinding> binding(
    noinline func: (VB.() -> Unit)? = null,
): MutableList<QuickAbility> {
    return bindWAdapter<VB> {
        func?.invoke(this)
    }
}

inline fun <reified VB : ViewDataBinding> bindWAdapter(
    noinline func: (VB.(adapter: QuickAdapter) -> Unit)? = null,
): MutableList<QuickAbility> {
    return bindWSelfWAdapter<VB, QuickAbilityData> { _, adapter ->
        func?.invoke(this, adapter)
    }
}

inline fun <reified VB : ViewDataBinding, T : QuickAbilityData> bindWSelf(
    noinline func: (VB.(self: T) -> Unit)? = null,
): MutableList<QuickAbility> {
    return bindWSelfWAdapter<VB, T> { self, _ ->
        func?.invoke(this, self)
    }
}

inline fun <reified VB : ViewDataBinding, T : QuickAbilityData> bindWSelfWAdapter(
    noinline func: (VB.(self: T, adapter: QuickAdapter) -> Unit)? = null,
): MutableList<QuickAbility> {
    return mutableListOf(
        object : QuickItemAbility<QuickAbilityData>, Unique {
            override fun getPrimeKey() = "binding"

            override fun beforeAdapterCreateHolder(
                mParent: ViewGroup,
                selfOnlyBase: QuickAbilityData,
            ) {
                super.beforeAdapterCreateHolder(mParent, selfOnlyBase)
                selfOnlyBase.bindingClass = VB::class.java
            }

            override fun afterOnBindHolder(context: Context, self: QuickAbilityData) {
                super.afterOnBindHolder(context, self)
                self.getViewBinding<VB>()?.let {
                    func?.invoke(it, self as T, self.getAdapter()!!)
                }
            }
        })
}