package com.wpf.app.quick.model

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.wpf.app.quick.adapterholder.TestHolder3
import com.wpf.app.quick.databinding.HolderTest3Binding
import com.wpf.app.quick.base.widgets.recyclerview.QuickItemDataBinding
import com.wpf.app.quick.base.widgets.recyclerview.HolderBindingClass

/**
 * Created by 王朋飞 on 2022/5/11.
 *
 */
@SuppressLint("NonConstantResourceId")
@HolderBindingClass(TestHolder3::class)
class TestModel3(var text: MutableLiveData<String>): QuickItemDataBinding<HolderTest3Binding>() {

}