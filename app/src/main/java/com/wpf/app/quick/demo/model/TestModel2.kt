package com.wpf.app.quick.demo.model

import com.wpf.app.quick.demo.databinding.HolderTest2Binding
import com.wpf.app.quickrecyclerview.holder.QuickViewBindingHolder
import com.wpf.app.quickrecyclerview.data.QuickViewDataBinding

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class TestModel2 : QuickViewDataBinding<HolderTest2Binding>() {
    var select2 = false
        set(value) {
            field = value
        }

    var title = ""
        set(value) {
            field = value
        }

    override fun onHolderCreated(viewHolder: QuickViewBindingHolder<out QuickViewDataBinding<HolderTest2Binding>, HolderTest2Binding>?) {
        super.onHolderCreated(viewHolder)
        viewHolder?.getItemView()?.setOnClickListener { v ->
            viewHolder.getAdapterClickListener()
                ?.onItemClick(v, viewHolder.getViewData(), viewHolder.bindingAdapterPosition)
        }
    }

    override fun toString(): String {
        return "TestModel2{" +
                "select2=" + select2 +
                ", title='" + title + '\'' +
                '}'
    }
}