package com.wpf.app.quickbind.interfaces

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

fun itemClick(listener: View.OnClickListener) = object : RunItemClick {
    override fun run(): View.OnClickListener {
        return listener
    }

    override fun run(self: Any): View.OnClickListener {
        return listener
    }
}

fun itemClickRun(listener: View.OnClickListener) = itemClick(listener).run()