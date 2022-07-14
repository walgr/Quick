package com.wpf.app.quick.adapterholder

import android.view.View
import android.view.ViewGroup
import com.wpf.app.quick.R
import com.wpf.app.quick.databinding.HolderTest3Binding
import com.wpf.app.quick.model.TestModel3
import com.wpf.app.quick.widgets.recyclerview.QuickViewBindingHolder

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class TestHolder3(mParent: ViewGroup) :
    QuickViewBindingHolder<TestModel3, HolderTest3Binding>(mParent, R.layout.holder_test3) {

    override fun onCreateHolderEnd(itemView: View) {
        super.onCreateHolderEnd(itemView)
        itemView.setOnClickListener {
            getAdapterClickListener()?.onItemClick(itemView, getViewData(), bindingAdapterPosition)
        }
    }
}