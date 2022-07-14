package com.wpf.app.quick.model

import androidx.lifecycle.MutableLiveData
import com.wpf.app.quick.adapterholder.TestHolder3
import com.wpf.app.quick.databinding.HolderTest3Binding
import com.wpf.app.quick.widgets.recyclerview.BindBindingHolder
import com.wpf.app.quick.widgets.recyclerview.QuickViewDataBinding

/**
 * Created by 王朋飞 on 2022/6/13.
 */
@BindBindingHolder(TestHolder3::class)
class TestModel3(val text: MutableLiveData<String>) : QuickViewDataBinding<HolderTest3Binding>()