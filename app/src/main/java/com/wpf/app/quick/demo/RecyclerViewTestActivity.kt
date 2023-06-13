package com.wpf.app.quick.demo

import com.wpf.app.quick.activity.QuickVBActivity
import com.wpf.app.quick.demo.databinding.ActivityRecyclerviewTestBinding
import com.wpf.app.quick.demo.viewmodel.RecyclerViewTestModel

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class RecyclerViewTestActivity :
    QuickVBActivity<RecyclerViewTestModel, ActivityRecyclerviewTestBinding>(
        R.layout.activity_recyclerview_test,
        titleName = "列表测试页"
    )