package com.wpf.app.quick.adapterholder

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quick.R
import com.wpf.app.quick.base.widgets.recyclerview.QuickViewBindingHolder
import com.wpf.app.quick.databinding.HolderMessageMyBinding
import com.wpf.app.quick.model.MyMessage

/**
 * Created by 王朋飞 on 2022/5/19.
 *
 */
class MyMessageHolder(mParent: ViewGroup) :
    QuickViewBindingHolder<MyMessage, HolderMessageMyBinding>(
        mParent = mParent,
        layoutId = R.layout.holder_message_my
    ) {

    override fun onCreateHolderEnd(itemView: View) {
        super.onCreateHolderEnd(itemView)
        viewBinding?.msg?.text = "${viewData?.userName}:${viewData?.msg}"
    }
}