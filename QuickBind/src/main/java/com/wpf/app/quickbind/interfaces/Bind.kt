package com.wpf.app.quickbind.interfaces

import android.os.Bundle
import android.view.View

/**
 * Created by 王朋飞 on 2022/7/20.
 * 可进行QuickBind.bind
 */
interface Bind {
    fun getView(): View?

    fun bindData(position: Int): Bundle? {
        return null
    }
}