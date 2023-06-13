package com.wpf.app.quickrecyclerview.bind

import android.view.View
import com.wpf.app.quickbind.annotations.BindD2VHelper
import com.wpf.app.quickrecyclerview.data.QuickRequestData
import com.wpf.app.quickrecyclerview.interfaces.Request2ViewWithView
import com.wpf.app.quickutil.Callback

object Request2View : BindD2VHelper<View, Request2ViewWithView<out QuickRequestData, out View>> {

    override fun initView(view: View, data: Request2ViewWithView<out QuickRequestData, out View>) {
        val callback = object : Callback<QuickRequestData> {
            override fun backData(data: QuickRequestData?) {
                data?.onCreateView(view)
            }
        }
        val realData = data as Request2ViewWithView<QuickRequestData, View>
        realData.view = view
        realData.callback = callback
        if (!realData.isManual) {
            realData.requestAndCallback(view, callback)
        }
    }

}