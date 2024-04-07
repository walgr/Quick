package com.wpf.app.quickutil.run

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunOnHolder<Return> : RunOnHolderWithSelf<Any, Return> {
    fun run(view: View): Return
    override fun run(view: View, self: Any): Return {
        return run(view)
    }
}

fun <Return> runOnHolder(primeKey: String = "", run: (View) -> Return) =
    object : RunOnHolder<Return> {
        override fun primeKey(): String {
            return primeKey
        }

        override fun run(view: View): Return {
            return run(view)
        }
    }