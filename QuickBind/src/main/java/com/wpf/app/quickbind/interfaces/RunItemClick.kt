package com.wpf.app.quickbind.interfaces

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunItemClick : RunOnHolder<View.OnClickListener> {

    fun run(): View.OnClickListener

    override fun run(view: View): View.OnClickListener {
        return run()
    }
}

fun itemClick(listener: View.OnClickListener) = object : RunItemClick {
    override fun run(): View.OnClickListener {
        return listener
    }
}