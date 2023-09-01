package com.wpf.app.quickutil.bind

import android.view.View

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface RunItemClickWithSelf<Self> : RunOnHolderWithSelf<View.OnClickListener, Self> {

    fun run(self: Self): View.OnClickListener

    override fun run(view: View, self: Self): View.OnClickListener {
        return run(self)
    }
}

fun <Self> itemClickWithSelf(run: (Self) -> View.OnClickListener) = object :
    RunItemClickWithSelf<Self> {
    override fun run(self: Self): View.OnClickListener {
        return run(self)
    }
}