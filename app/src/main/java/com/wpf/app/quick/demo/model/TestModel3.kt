package com.wpf.app.quick.demo.model

import androidx.lifecycle.MutableLiveData
import com.wpf.app.quick.demo.adapterholder.TestHolder3
import com.wpf.app.quick.demo.databinding.HolderTest3Binding
import com.wpf.app.quickrecyclerview.holder.BindBindingHolder
import com.wpf.app.quickrecyclerview.data.QuickViewDataBinding

/**
 * Created by 王朋飞 on 2022/6/13.
 */
@com.wpf.app.quickrecyclerview.holder.BindBindingHolder(TestHolder3::class)
class TestModel3(val text: MutableLiveData<String>) : com.wpf.app.quickrecyclerview.data.QuickViewDataBinding<HolderTest3Binding>()