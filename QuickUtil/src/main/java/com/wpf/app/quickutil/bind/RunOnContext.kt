package com.wpf.app.quickutil.bind

import android.content.Context
import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunOnContext<Data> : RunOnHolder<Data> {
    fun run(context: Context): Data

    override fun run(view: View): Data {
        return run(view.context)
    }
}

fun <Data> runOnContext(run: (Context) -> Data) = object : RunOnContext<Data> {
    override fun run(context: Context): Data {
        return run(context)
    }
}