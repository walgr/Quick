package com.wpf.app.quickutil.bind

import android.content.Context
import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunOnContext<Return> : RunOnHolder<Return> {
    fun run(context: Context): Return

    override fun run(view: View): Return {
        return run(view.context)
    }
}

fun <Return> runOnContext(primeKey: String = "", run: (Context) -> Return) =
    object : RunOnContext<Return> {
        override fun primeKey(): String {
            return primeKey
        }

        override fun run(context: Context): Return {
            return run(context)
        }
    }