package com.wpf.app.quickutil.run

import android.content.Context
import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunOnContextWithSelf<Self, Return> : RunOnViewWithSelf<Self, Return> {
    fun run(context: Context, self: Self): Return

    override fun run(view: View, self: Self): Return {
        return run(view.context, self)
    }
}

inline fun <Self, Return> runOnContextWithSelf(
    primeKey: String = "",
    crossinline run: Self.(Context) -> Return,
) = object : RunOnContextWithSelf<Self, Return> {
    override fun primeKey(): String {
        return primeKey
    }

    override fun run(context: Context, self: Self): Return {
        return run(self, context)
    }
}