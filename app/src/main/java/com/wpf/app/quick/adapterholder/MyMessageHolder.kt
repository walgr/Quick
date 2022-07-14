package com.wpf.app.quick.adapterholder

import android.view.ViewGroup
import com.wpf.app.quick.R
import com.wpf.app.quick.databinding.HolderMessageMyBinding
import com.wpf.app.quick.model.MyMessage
import com.wpf.app.quick.widgets.recyclerview.QuickAdapter
import com.wpf.app.quick.widgets.recyclerview.QuickViewBindingHolder

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class MyMessageHolder(mParent: ViewGroup) :
    QuickViewBindingHolder<MyMessage, HolderMessageMyBinding>(
        mParent,
        R.layout.holder_message_my
    ) {

    override fun onBindViewHolder(adapter: QuickAdapter, data: MyMessage?, position: Int) {}
}