package com.wpf.app.quick.demo.model

import com.wpf.app.quick.demo.R
import com.wpf.app.quickrecyclerview.data.QuickBindData
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class MyMessage(
    var userName: String = "",
    var msg: String = ""
) : QuickBindData(R.layout.holder_message_my, autoSet = true), Serializable