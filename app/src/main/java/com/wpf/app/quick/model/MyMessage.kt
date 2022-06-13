package com.wpf.app.quick.model

import com.wpf.app.quick.adapterholder.MyMessageHolder
import com.wpf.app.quick.base.widgets.recyclerview.QuickBindingData
import com.wpf.app.quick.base.widgets.recyclerview.HolderBindingClass
import com.wpf.app.quick.databinding.HolderMessageMyBinding
import java.io.Serializable

/**
 * Created by 王朋飞 on 2022/5/19.
 *
 */
@HolderBindingClass(holderClass = MyMessageHolder::class)
class MyMessage(
    var userName: String = "",
    var msg: String = ""
) : QuickBindingData<HolderMessageMyBinding>(), Serializable