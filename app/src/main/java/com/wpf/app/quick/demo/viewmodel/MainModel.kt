package com.wpf.app.quick.demo.viewmodel

import android.annotation.SuppressLint
import android.widget.TextView
import com.wpf.app.quick.demo.R
import com.wpf.app.quick.annotations.bind.BindView
import com.wpf.app.quick.demo.databinding.ActivityMainBinding
import com.wpf.app.quick.activity.viewmodel.QuickVBModel
import com.wpf.app.quickbind.annotations.BindSp2View

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class MainModel : QuickVBModel<ActivityMainBinding>() {

    @SuppressLint("NonConstantResourceId", "StaticFieldLeak")
    @BindSp2View(bindSp = "绑定的SpKey2", defaultValue = "默认值2")
    @BindView(R.id.spTextView2)
    var text2: TextView? = null

    override fun onBindingCreated(view: ActivityMainBinding?) {

    }
}