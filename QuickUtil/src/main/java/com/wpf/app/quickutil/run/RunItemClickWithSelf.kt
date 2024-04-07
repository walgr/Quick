package com.wpf.app.quickutil.run

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunItemClickWithSelf<Self> : RunOnHolderWithSelf<Self, View.OnClickListener> {

    fun run(self: Self): View.OnClickListener

    override fun run(view: View, self: Self): View.OnClickListener {
        return run(self)
    }
}

fun <Self> itemClickWithSelf(primeKey: String = "", run: Self.(View) -> Unit) = object :
    RunItemClickWithSelf<Self> {
    override fun primeKey(): String {
        return primeKey
    }

    override fun run(self: Self): View.OnClickListener {
        return View.OnClickListener {
            run(self, it)
        }
    }
}