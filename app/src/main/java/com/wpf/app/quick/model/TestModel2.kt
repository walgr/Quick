package com.wpf.app.quick.model

import android.annotation.SuppressLint
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quick.BR
import com.wpf.app.quick.R
import com.google.gson.Gson
import com.wpf.app.quick.base.widgets.recyclerview.CommonItemData
import com.wpf.app.quick.base.widgets.recyclerview.CommonItemDataBinding
import com.wpf.app.quick.base.widgets.recyclerview.CommonViewBindingHolder
import com.wpf.app.quick.base.widgets.recyclerview.HolderBindingLayout
import com.wpf.app.quick.databinding.HolderTest2Binding

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */
@SuppressLint("NonConstantResourceId")
@HolderBindingLayout(R.layout.holder_test2)
class TestModel2(
) : CommonItemDataBinding<HolderTest2Binding>() {

    //只能data -> View单向刷新，view -> data需要设置点击监听
    @Bindable
    var select2: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.select2)
        }

    @Bindable
    var title: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.title)
        }

    override fun toString(): String {
        return Gson().toJson(this)
    }

    override fun onCreateHolderEnd(viewHolder: CommonViewBindingHolder<out CommonItemDataBinding<HolderTest2Binding>, HolderTest2Binding>) {
        super.onCreateHolderEnd(viewHolder)
        viewHolder.itemView.setOnClickListener {
            viewHolder.getAdapterClickListener()?.onItemClick(
                it,
                viewHolder.viewData as CommonItemData,
                viewHolder.bindingAdapterPosition
            )
        }
    }
}