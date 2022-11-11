package com.wpf.app.quickbind.helper.binddatahelper

import android.view.View
import com.wpf.app.quickbind.bindview.QuickRequestData
import com.wpf.app.quickbind.annotations.BindD2VHelper
import com.wpf.app.quickbind.interfaces.Request2ViewWithView
import com.wpf.app.quickutil.Callback

object Request2View: BindD2VHelper<View, Request2ViewWithView<out QuickRequestData, out View>> {

    override fun initView(view: View, data: Request2ViewWithView<out QuickRequestData, out View>) {
        (data as Request2ViewWithView<QuickRequestData, View>).requestAndCallback(view, callback = object : Callback<QuickRequestData> {
            override fun callback(data: QuickRequestData?) {
                data?.onCreateView(view)
            }

        })
    }

}