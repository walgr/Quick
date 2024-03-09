package com.wpf.app.quick.activity

import android.view.View
import androidx.annotation.LayoutRes
import com.wpf.app.quickutil.bind.RunOnContext
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

fun <T : QuickActivityAbility> List<T>.with(others: List<T>): List<T> {
    val newList = mutableListOf<T>()
    newList.addAll(this)
    newList.addAll(others)
    return newList
}

fun <T : QuickActivityAbility> List<T>.with(other: T): List<T> {
    val newList = mutableListOf<T>()
    newList.addAll(this)
    newList.add(other)
    return newList
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

interface QuickInflateViewAbility : QuickActivityAbility {
    override fun getPrimeKey() = "inflateView"
    fun layoutId(): Int = 0
    fun layoutView(): View? = null

    fun layoutViewInContext(): RunOnContext<View>? = null
}
