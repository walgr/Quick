package com.wpf.app.quick.helper.binddatahelper

import android.view.View
import com.wpf.app.quickbind.annotations.BindD2VHelper
import com.wpf.app.quickrecyclerview.data.QuickItemData
import com.wpf.app.quickrecyclerview.listeners.RequestAndCallbackWithView

object Request2View: BindD2VHelper<View, RequestAndCallbackWithView<out QuickItemData, out View>> {
    override fun initView(view: View, data: RequestAndCallbackWithView<out QuickItemData, out View>) {

    }

}