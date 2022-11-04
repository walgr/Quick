package com.wpf.app.quick.demo

import android.content.Context
import android.util.AttributeSet
import com.wpf.app.quick.activity.QuickViewModelActivity
import com.wpf.app.quick.demo.viewmodel.RequestTestViewModel
import com.wpf.app.quick.widgets.quickview.helper.GotoThis

class RequestTestActivity : QuickViewModelActivity<RequestTestViewModel>(
    R.layout.activity_request_test,
    titleName = "接口测试页"
) {
    class GotoRequestTest(
        mContext: Context,
        attributeSet: AttributeSet? = null,
    ): GotoThis(
        mContext, attributeSet, "接口请求测试", RequestTestActivity::class.java
    )
}