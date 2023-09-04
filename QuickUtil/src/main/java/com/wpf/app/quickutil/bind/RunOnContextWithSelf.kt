package com.wpf.app.quickutil.bind

import android.content.Context
import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunOnContextWithSelf<Data, Self> : RunOnHolderWithSelf<Data, Self> {
    fun run(context: Context, self: Self): Data

    override fun run(view: View, self: Self): Data {
        return run(view.context, self)
    }
}

inline fun <Data, Self> runOnContextWithSelf(crossinline run: (Context, Self) -> Data) = object :
    RunOnContextWithSelf<Data, Self> {
    override fun run(context: Context, self: Self): Data {
        return run(context, self)
    }
}