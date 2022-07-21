package com.wpf.app.quickbind.interfaces

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunOnHolderWithSelf<Data, Self> {
    fun run(view: View, self: Self): Data
}

fun <Data, Self> runOnHolderWithSelf(run: (View, Self) -> Data) = object : RunOnHolderWithSelf<Data, Self> {
    override fun run(view: View, self: Self): Data {
        return run(view, self)
    }
}