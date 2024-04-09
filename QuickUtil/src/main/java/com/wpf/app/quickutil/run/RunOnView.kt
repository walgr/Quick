package com.wpf.app.quickutil.run

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunOnView<Return> : RunOnViewWithSelf<Any, Return> {
    fun run(view: View): Return
    override fun run(view: View, self: Any): Return {
        return run(view)
    }
}

fun <Return> runOnView(primeKey: String = "", run: View.() -> Return) =
    object : RunOnView<Return> {
        override fun primeKey(): String {
            return primeKey
        }

        override fun run(view: View): Return {
            return run(view)
        }
    }