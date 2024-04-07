package com.wpf.app.quickutil.run

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunOnHolderWithSelf<Self, Return> {
    fun run(view: View, self: Self): Return

    fun primeKey(): String {
        return ""
    }
}

fun <Self, Return> runOnHolderWithSelf(run: (View, Self) -> Return, primeKey: String = "") =
    object :
        RunOnHolderWithSelf<Self, Return> {
        override fun primeKey(): String {
            return primeKey
        }

        override fun run(view: View, self: Self): Return {
            return run(view, self)
        }
    }