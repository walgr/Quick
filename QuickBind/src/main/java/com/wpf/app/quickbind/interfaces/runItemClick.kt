package com.wpf.app.quickbind.interfaces

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface runItemClick : runOnView<View.OnClickListener> {

    fun run(): View.OnClickListener

    override fun run(view: View): View.OnClickListener {
        return run()
    }
}