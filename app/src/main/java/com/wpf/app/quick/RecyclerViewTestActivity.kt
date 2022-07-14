package com.wpf.app.quick

import com.wpf.app.quick.activity.ViewModelBindingActivity
import com.wpf.app.quick.databinding.ActivityRecyclerviewTestBinding
import com.wpf.app.quick.viewmodel.RecyclerViewTestViewModel

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class RecyclerViewTestActivity :
    ViewModelBindingActivity<RecyclerViewTestViewModel, ActivityRecyclerviewTestBinding>(
        R.layout.activity_recyclerview_test,
        titleName = "列表测试页"
    )