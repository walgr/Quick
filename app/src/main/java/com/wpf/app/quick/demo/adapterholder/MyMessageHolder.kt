package com.wpf.app.quick.demo.adapterholder

import android.view.ViewGroup
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.demo.databinding.HolderMessageMyBinding
import com.wpf.app.quick.demo.model.MyMessage
import com.wpf.app.quickrecyclerview.QuickAdapter
import com.wpf.app.quickrecyclerview.holder.QuickViewBindingHolder

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class MyMessageHolder(mParent: ViewGroup) :
    QuickViewBindingHolder<MyMessage, HolderMessageMyBinding>(
        mParent,
        R.layout.holder_message_my
    )