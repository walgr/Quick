package com.wpf.app.quick.adapterholder

import android.view.ViewGroup
import com.wpf.app.quick.R
import com.wpf.app.quick.base.widgets.recyclerview.CommonViewBindingHolder
import com.wpf.app.quick.databinding.HolderTest3Binding
import com.wpf.app.quick.model.TestModel3

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */

class TestHolder3(mParent: ViewGroup)
    : CommonViewBindingHolder<TestModel3, HolderTest3Binding>(mParent, layoutId = R.layout.holder_test3) {

    override fun onCreateHolderEnd() {
        super.onCreateHolderEnd()
        itemView.setOnClickListener {
            getAdapterClickListener()?.onItemClick(itemView, viewData, bindingAdapterPosition)
        }
    }
}