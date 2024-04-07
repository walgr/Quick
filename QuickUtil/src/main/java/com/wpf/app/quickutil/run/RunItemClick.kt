package com.wpf.app.quickutil.run

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunItemClick : RunItemClickWithSelf<Any> {

    fun run(): View.OnClickListener

    override fun run(view: View, self: Any): View.OnClickListener {
        return run()
    }
}

fun itemClick(primeKey: String = "", listener: View.OnClickListener) = object : RunItemClick {
    override fun primeKey(): String {
        return primeKey
    }

    override fun run(): View.OnClickListener {
        return listener
    }

    override fun run(self: Any): View.OnClickListener {
        return listener
    }
}