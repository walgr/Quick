package com.wpf.app.quickutil.run

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunOnViewWithSelf<Self, Return> {
    fun run(view: View, self: Self): Return

    fun primeKey(): String {
        return ""
    }
}

fun <Self, Return> runOnViewWithSelf(run: Self.(View) -> Return, primeKey: String = "") =
    object : RunOnViewWithSelf<Self, Return> {
        override fun primeKey(): String {
            return primeKey
        }

        override fun run(view: View, self: Self): Return {
            return run(self, view)
        }
    }