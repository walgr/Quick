package com.wpf.app.quickbind.interfaces

import androidx.lifecycle.ViewModel

/**
 * Created by 王朋飞 on 2022/7/12.
 *
 */
interface BindViewModel<VM : ViewModel> {

    fun getViewModel(): VM?
}