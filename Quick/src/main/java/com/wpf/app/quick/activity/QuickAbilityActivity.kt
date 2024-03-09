package com.wpf.app.quick.activity

import android.view.View
import androidx.annotation.LayoutRes
import com.wpf.app.quickutil.bind.RunOnContext
import com.wpf.app.quickutil.other.Unique
import com.wpf.app.quickutil.other.to

open class QuickAbilityActivity(
    private val abilityList: List<QuickActivityAbility> = mutableListOf(),
    titleName: String = ""
) : QuickActivity(
    layoutId = abilityList.find { it is QuickInflateViewAbility }!!.to<QuickInflateViewAbility>()
        .layoutId(),
    layoutView = abilityList.find { it is QuickInflateViewAbility }!!.to<QuickInflateViewAbility>()
        .layoutView(),
    layoutViewInContext = abilityList.find { it is QuickInflateViewAbility }!!
        .to<QuickInflateViewAbility>().layoutViewInContext(),
    titleName = titleName
) {

    override fun initView(view: View) {
        getDropInflateView().forEach {
            it.initView(view)
        }
    }

    private fun getDropInflateView(): List<QuickActivityAbility> {
        return abilityList.dropWhile { it is QuickInflateViewAbility }
    }

}

fun <T : QuickActivityAbility> MutableList<T>.with(others: List<T>): MutableList<T> {
    others.filter { it is Unique }.map { it.getPrimeKey() }.forEach { otherPrimeKey ->
        this.remove(this.find { it.getPrimeKey() == otherPrimeKey })
    }
    this.addAll(others)
    return this
}

fun <T : QuickActivityAbility> MutableList<T>.with(other: T): MutableList<T> {
    if (other is Unique) {
        this.remove(this.find { it.getPrimeKey() == other.getPrimeKey() })
    }
    this.add(other)
    return this
}

fun inflate(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null
): List<QuickActivityAbility> {
    return mutableListOf(object : QuickInflateViewAbility {
        override fun layoutId(): Int {
            return layoutId
        }

        override fun layoutView(): View? {
            return layoutView
        }

        override fun layoutViewInContext(): RunOnContext<View>? {
            return layoutViewInContext
        }

    })
}


interface QuickActivityAbility {
    fun getPrimeKey(): String

    fun onInflateView(activity: QuickActivity) {

    }

    fun initView(view: View) {

    }
}

interface QuickInflateViewAbility : QuickActivityAbility, Unique {
    override fun getPrimeKey() = "inflateView"
    fun layoutId(): Int = 0
    fun layoutView(): View? = null

    fun layoutViewInContext(): RunOnContext<View>? = null
}
