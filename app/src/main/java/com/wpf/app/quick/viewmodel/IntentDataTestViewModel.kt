package com.wpf.app.quick.viewmodel

import android.widget.TextView
import com.wpf.app.quick.R
import com.wpf.app.quick.base.helper.AutoGet
import com.wpf.app.quick.base.helper.FindView
import com.wpf.app.quick.base.viewmodel.BindingViewModel
import com.wpf.app.quick.databinding.ActivityDataTestBinding
import com.wpf.app.quick.model.MyMessage
import com.wpf.app.quick.model.TestModel

/**
 * Created by 王朋飞 on 2022/6/9.
 *
 */
class IntentDataTestViewModel(
    @AutoGet val intD: Int = 0,
    @AutoGet val byteD: Byte = 0,
    @AutoGet val floatD: Float = 0f,
    @AutoGet val doubleD: Double = 0.0,
    @AutoGet val charD: Char = 'a',
    @AutoGet val data: MyMessage? = null,
    @AutoGet val data1: TestModel? = null,
    @AutoGet val map: Map<String, Any>? = null,
    @AutoGet val list: List<Any>? = null,
    @AutoGet val array: Array<Any>? = null,
): BindingViewModel<ActivityDataTestBinding>() {

    @FindView(R.id.title)
    var title: TextView? = null

    override fun onBindingCreate(viewBinding: ActivityDataTestBinding?) {
        super.onBindingCreate(viewBinding)
        printData()
    }

    private fun printData() {
        println(intD)
        println(floatD)
        println(doubleD)
        println(charD)
        println(byteD)
        println(data)
        println(data1)
        println(map)
        println(list)
        println(array?.get(0))
        title?.text = "数据测试页ViewModel"
    }
}