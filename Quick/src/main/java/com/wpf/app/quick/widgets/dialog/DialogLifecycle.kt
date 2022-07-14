package com.wpf.app.quick.widgets.dialog

/**
 * Created by 王朋飞 on 2022/6/21.
 */
interface DialogLifecycle {
    //dialog show之前执行
    fun onDialogPrepare() {}

    //dialog show之后执行
    fun onDialogOpen() {}

    //dialog dismiss后执行
    fun onDialogClose() {}
}