package com.wpf.app.quick.ability.ex

import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quick.activity.viewmodel.ViewLifecycle
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickutil.bind.RunOnContext
import com.wpf.app.quickutil.helper.match
import com.wpf.app.quickutil.helper.matchLayoutParams
import com.wpf.app.quickutil.other.Unique
import com.wpf.app.quickutil.other.forceTo

fun <T : QuickActivityAbility> MutableList<T>.with(others: MutableList<T>): MutableList<T> {
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

fun <T : QuickActivityAbility> T.with(others: MutableList<T>): MutableList<T> {
    if (this is Unique) {
        others.remove(others.find { it.getPrimeKey() == this.getPrimeKey() })
    }
    others.add(this)
    return others
}

fun <T : QuickActivityAbility> T.with(other: T): MutableList<T> {
    val abilityList = mutableListOf<T>()
    abilityList.add(this)
    if (other !is Unique || other.getPrimeKey() != this.getPrimeKey()) {
        abilityList.add(other)
    }
    return abilityList
}

@Deprecated("框架使用,建议使用contentView")
fun setContentViewCommon(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    contentBuilder: (QuickView.(view: View) -> Unit)? = null,
): MutableList<QuickActivityAbility> {
    return setContentView(layoutId, layoutView, layoutViewInContext) {
        contentBuilder?.invoke(this, it)
        null
    }
}

@Deprecated("框架使用,建议使用contentView")
fun setContentView(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    contentBuilder: (QuickView.(view: View) -> View?)? = null,
): MutableList<QuickActivityAbility> {
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

        override fun beforeDealContentView(owner: ViewModelStoreOwner, view: View): View {
            super.beforeDealContentView(owner, view)
            if (view.layoutParams != null) {
                view.layoutParams?.width = match
                view.layoutParams?.height = match
            } else {
                view.layoutParams = matchLayoutParams
            }
            return contentBuilder?.invoke(owner.forceTo(), view) ?: view
        }
    })
}

interface QuickActivityAbility : ViewLifecycle {
    fun getPrimeKey(): String

    fun dealContentView(owner: ViewModelStoreOwner, view: View) {

    }

    fun initView(view: View) {

    }
}

interface QuickInflateViewAbility : QuickActivityAbility, Unique {
    override fun getPrimeKey() = "inflateView"
    fun layoutId(): Int = 0
    fun layoutView(): View? = null
    fun layoutViewInContext(): RunOnContext<View>? = null

    fun beforeDealContentView(owner: ViewModelStoreOwner, view: View): View {
        return view
    }
}

interface QuickVMAbility<VM : ViewModel> : QuickActivityAbility, Unique, BindViewModel<VM> {
    override fun getPrimeKey() = "viewModel"
}

interface QuickFragmentAbility : QuickActivityAbility {
    fun setUserVisibleHint(isVisibleToUser: Boolean)
}