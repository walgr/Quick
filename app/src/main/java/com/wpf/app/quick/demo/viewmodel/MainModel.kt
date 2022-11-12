package com.wpf.app.quick.demo.viewmodel

import android.annotation.SuppressLint
import android.widget.TextView
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.annotations.BindData2View
import com.wpf.app.quick.annotations.BindView
import com.wpf.app.quick.demo.databinding.ActivityMainBinding
import com.wpf.app.quickbind.helper.binddatahelper.Text2TextView
import com.wpf.app.quick.activity.viewmodel.QuickBindingModel
import com.wpf.app.quickbind.annotations.BindSp2View

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class MainModel : QuickBindingModel<ActivityMainBinding>() {

    @SuppressLint("NonConstantResourceId")
    @BindData2View(id = R.id.spTextView1, helper = Text2TextView::class)
    var title = "测试BindData2View"

    @SuppressLint("NonConstantResourceId", "StaticFieldLeak")
    @BindSp2View(bindSp = "绑定的SpKey2", defaultValue = "默认值2")
    @BindView(R.id.spTextView2)
    var text2: TextView? = null

    override fun onBindingCreated(mViewBinding: ActivityMainBinding?) {

    }
}