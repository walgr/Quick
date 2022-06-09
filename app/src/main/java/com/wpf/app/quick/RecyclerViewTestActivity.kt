package com.wpf.app.quick

import com.wpf.app.quick.base.activity.ViewModelBindingActivity
import com.wpf.app.quick.databinding.ActivityRecyclerviewTestBinding
import com.wpf.app.quick.viewmodel.RecyclerViewTestViewModel

/**
 * Created by 王朋飞 on 2022/6/8.
 *r
 */
class RecyclerViewTestActivity :
    ViewModelBindingActivity<RecyclerViewTestViewModel, ActivityRecyclerviewTestBinding>(
        R.layout.activity_recyclerview_test,
        activityTitle = "列表测试页",
    )