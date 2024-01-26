package com.wpf.app.quickutil.bind

import android.content.Context
import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunOnContextWithSelf<Self, Return> : RunOnHolderWithSelf<Self, Return> {
    fun run(context: Context, self: Self): Return

    override fun run(view: View, self: Self): Return {
        return run(view.context, self)
    }
}

inline fun <Self, Return> runOnContextWithSelf(crossinline run: (Context, Self) -> Return) =
    object :
        RunOnContextWithSelf<Self, Return> {
        override fun run(context: Context, self: Self): Return {
            return run(context, self)
        }
    }