package com.wpf.app.quick.demo.viewmodel

import android.annotation.SuppressLint
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.demo.databinding.ActivityMainBinding
import com.wpf.app.quick.helper.binddatahelper.Text2TextView
import com.wpf.app.quick.viewmodel.BindingViewModel

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class MainViewModel : BindingViewModel<ActivityMainBinding>() {

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.spTextView1, helper = Text2TextView::class)
    var title = "测试BindData2View"

    override fun onBindingCreated(mViewBinding: ActivityMainBinding?) {

    }
}