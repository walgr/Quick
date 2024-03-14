package com.wpf.app.quick.ability

import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.wpf.app.quick.activity.QuickView
import com.wpf.app.quick.activity.viewmodel.ViewLifecycle
import com.wpf.app.quickbind.interfaces.BindViewModel
import com.wpf.app.quickutil.bind.RunOnContext
import com.wpf.app.quickutil.other.Unique
import com.wpf.app.quickutil.other.forceTo


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

fun setContentView(
    @LayoutRes layoutId: Int = 0,
    layoutView: View? = null,
    layoutViewInContext: RunOnContext<View>? = null,
    contentBuilder: (QuickView.(view: View) -> View)? = null,
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
            return contentBuilder?.invoke(owner.forceTo(), view) ?: view
        }
    })
}

fun dealBind(
    dealBind: Boolean = true
): MutableList<QuickActivityAbility> {
    return mutableListOf(object : QuickDealBindAbility {
        override fun dealBind() = dealBind
    })
}

interface QuickActivityAbility {
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

interface QuickDealBindAbility : QuickActivityAbility, Unique {
    override fun getPrimeKey() = "dealBind"
    fun dealBind(): Boolean = true
}

interface QuickVMAbility<VM : ViewModel> : QuickActivityAbility, Unique, ViewLifecycle,
    BindViewModel<VM> {
    override fun getPrimeKey() = "viewModel"
}