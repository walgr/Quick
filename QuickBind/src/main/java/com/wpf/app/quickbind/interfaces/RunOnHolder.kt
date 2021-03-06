package com.wpf.app.quickbind.interfaces

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunOnHolder<Data> : RunOnHolderWithSelf<Data, Any> {
    fun run(view: View): Data
    override fun run(view: View, self: Any): Data {
        return run(view)
    }
}

fun <Data> runOnHolder(run: (View) -> Data) = object : RunOnHolder<Data> {
    override fun run(view: View): Data {
        return run(view)
    }
}