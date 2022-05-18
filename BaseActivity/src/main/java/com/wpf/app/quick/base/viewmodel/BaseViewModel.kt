package com.wpf.app.quick.base.viewmodel

import androidx.lifecycle.ViewModel
import com.wpf.app.quick.base.activity.BaseView

/**
 * Created by 王朋飞 on 2022/4/2.
 * Activity基础控制器
 */
open class BaseViewModel<T : BaseView>(val baseView: T) : ViewModel() {

    fun onModelCreate(baseView: T) {

    }
}