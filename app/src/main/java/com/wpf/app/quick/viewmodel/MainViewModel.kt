package com.wpf.app.quick.viewmodel

import android.annotation.SuppressLint
import com.wpf.app.quick.R
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.databinding.ActivityMainBinding
import com.wpf.app.quick.helper.binddatahelper.Text2TextView

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