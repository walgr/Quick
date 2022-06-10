package com.wpf.app.quick

import android.annotation.SuppressLint
import android.widget.TextView
import com.wpf.app.quick.base.activity.ViewModelBindingActivity
import com.wpf.app.quick.base.helper.AutoGet
import com.wpf.app.quick.base.helper.FindView
import com.wpf.app.quick.databinding.ActivityDataTestBinding
import com.wpf.app.quick.viewmodel.IntentDataTestViewModel

/**
 * Created by 王朋飞 on 2022/6/8.
 *r
 */
class IntentDataTestActivity(
    @AutoGet override val activityTitle: String = "传输测试页"
) :
    ViewModelBindingActivity<IntentDataTestViewModel, ActivityDataTestBinding>(
        R.layout.activity_data_test,
        activityTitle = activityTitle
    ) {

    @SuppressLint("NonConstantResourceId")
    @FindView(R.id.title1)
    val title: TextView? = null

    @SuppressLint("SetTextI18n")
    override fun initView(viewDataBinding: ActivityDataTestBinding?) {
        super.initView(viewDataBinding)
        title?.text = "传输测试页Activity"
    }
}