package com.wpf.app.quickutil.bind

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunOnHolderWithSelf<Self, Return> {
    fun run(view: View, self: Self): Return
}

fun <Self, Return> runOnHolderWithSelf(run: (View, Self) -> Return) = object :
    RunOnHolderWithSelf<Self, Return> {
    override fun run(view: View, self: Self): Return {
        return run(view, self)
    }
}