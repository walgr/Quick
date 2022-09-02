package com.wpf.app.quick.demo.model

import com.wpf.app.quick.demo.adapterholder.MyMessageHolder
import com.wpf.app.quick.demo.databinding.HolderMessageMyBinding
import com.wpf.app.quick.widgets.recyclerview.holder.BindBindingHolder
import com.wpf.app.quick.widgets.recyclerview.data.QuickViewDataBinding
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/6/13.
 */
@BindBindingHolder(MyMessageHolder::class)
class MyMessage(
    var userName: String = "",
    var msg: String = ""
) : QuickViewDataBinding<HolderMessageMyBinding>(), Serializable