package com.wpf.app.quick.adapterholder

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quick.R
import com.wpf.app.quick.base.widgets.recyclerview.QuickViewBindingHolder
import com.wpf.app.quick.databinding.HolderTest3Binding
import com.wpf.app.quick.model.TestModel3

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */

class TestHolder3(mParent: ViewGroup)
    : QuickViewBindingHolder<TestModel3, HolderTest3Binding>(mParent, layoutId = R.layout.holder_test3) {

    override fun onCreateHolderEnd(itemView: View) {
        super.onCreateHolderEnd(itemView)
        itemView.setOnClickListener {
            getAdapterClickListener()?.onItemClick(itemView, viewData, bindingAdapterPosition)
        }
    }
}