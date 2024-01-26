package com.wpf.app.quickrecyclerview.data

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.wpf.app.quickutil.ability.AbilityManager
import com.wpf.app.quickutil.ability.QuickAbility
import com.wpf.app.quickutil.ability.QuickContextAbility
import com.wpf.app.quickutil.bind.RunOnContextWithSelf
import com.wpf.app.quickutil.bind.runOnContextWithSelf
import com.wpf.app.quickutil.helper.toView

fun <T : QuickContextAbility<*, *>> List<T>.with(others: List<T>): List<T> {
    val newList = mutableListOf<T>()
    newList.addAll(this)
    newList.addAll(others)
    return newList
}

fun <T : QuickContextAbility<*, *>> List<T>.with(other: T): List<T> {
    val newList = mutableListOf<T>()
    newList.addAll(this)
    newList.add(other)
    return newList
}

open class QuickAbilityData(
    @Transient @LayoutRes override var layoutId: Int = 0,
    @Transient override var layoutViewInContext: RunOnContextWithSelf<ViewGroup, View>? = null,
    @Transient override val isDealBinding: Boolean = false,      //是否处理DataBinding
    @Transient override var autoSet: Boolean = false,             //自动映射
    @Transient override var isSuspension: Boolean = false,      //View是否悬浮置顶
    @Transient val abilityList: List<QuickContextAbility<*, *>> = mutableListOf()
) : QuickBindData() {

    override fun onCreateViewHolder(itemView: View) {
        super.onCreateViewHolder(itemView)
        AbilityManager.dealByThisAndContext<QuickAbilityData>(
            abilityList as List<QuickContextAbility<QuickAbilityData, *>>,
                    itemView.context, this
        )
    }
}

fun suspension(func: () -> Boolean): List<QuickAbility<*, *>> {
    return mutableListOf(object : QuickAbility<QuickAbilityData, Unit> {
        override fun run(self: QuickAbilityData) {
            self.isSuspension = func.invoke()
        }
    })
}

fun <VB : ViewDataBinding, T: QuickAbilityData> binding(
    layoutId: Int = 0,
    layoutViewInContext: RunOnContextWithSelf<T, View>? = null,
    func: VB.(self: T) -> Unit
): List<QuickContextAbility<*, *>> {
    return suspension { false }.with(
        object : QuickContextAbility<T, Unit> {
            override fun run(context: Context, self: T) {
                self.layoutId = layoutId
                layoutViewInContext?.let {
                    self.layoutViewInContext = runOnContextWithSelf { _, _ ->
                        return@runOnContextWithSelf it.run(context, self)
                    }
                }
                val vb = DataBindingUtil.bind<VB>(
                    if (layoutId != 0) {
                        layoutId.toView(context)
                    } else {
                        layoutViewInContext!!.run(context, self)
                    }
                ) ?: return
                func.invoke(vb, self)
            }
        })
}