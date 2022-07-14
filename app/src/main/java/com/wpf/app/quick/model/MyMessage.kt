package com.wpf.app.quick.model

import com.wpf.app.quick.adapterholder.MyMessageHolder
import com.wpf.app.quick.databinding.HolderMessageMyBinding
import com.wpf.app.quick.widgets.recyclerview.BindBindingHolder
import com.wpf.app.quick.widgets.recyclerview.QuickViewDataBinding
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/6/13.
 */
@BindBindingHolder(MyMessageHolder::class)
class MyMessage(
    var userName: String = "",
    var msg: String = ""
) : QuickViewDataBinding<HolderMessageMyBinding>(), Serializable