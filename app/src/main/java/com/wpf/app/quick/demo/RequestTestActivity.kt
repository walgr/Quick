package com.wpf.app.quick.demo

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import com.wpf.app.quick.activity.QuickViewModelActivity
import com.wpf.app.quick.demo.viewmodel.RequestTestViewModel
import com.wpf.app.quick.widgets.quickview.QuickBindView
import com.wpf.app.quickbind.interfaces.runOnContext
import com.wpf.app.quickutil.startActivity

class RequestTestActivity : QuickViewModelActivity<RequestTestViewModel, RequestTestActivity>(
    R.layout.activity_request_test,
    titleName = "接口测试页"
) {


    class GotoThis @JvmOverloads constructor(
        mContext: Context,
        attributeSet: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : QuickBindView(mContext, attributeSet, defStyleAttr, layoutView = runOnContext { context ->
        Button(context).apply {
            text = "接口测试页"
            setOnClickListener {
                context.startActivity(activityCls = RequestTestActivity::class.java)
            }
        }
    })
}