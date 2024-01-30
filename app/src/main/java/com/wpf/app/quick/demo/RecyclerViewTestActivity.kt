package com.wpf.app.quick.demo

import com.wpf.app.quick.activity.QuickListVBActivity
import com.wpf.app.quick.demo.databinding.ActivityRecyclerviewTestBinding
import com.wpf.app.quick.demo.model.BindDataTestModel
import com.wpf.app.quick.demo.viewmodel.RecyclerViewTestModel

/**
 * Created by 王朋飞 on 2022/6/13.
 */
class RecyclerViewTestActivity :
    QuickListVBActivity<RecyclerViewTestModel, ActivityRecyclerviewTestBinding>(
        dataList = listOf(
            BindDataTestModel(),
            BindDataTestModel(),
            BindDataTestModel(),
            BindDataTestModel(),
            BindDataTestModel(),
            BindDataTestModel(),
            BindDataTestModel(),
            BindDataTestModel(),
            BindDataTestModel(),
            BindDataTestModel(),
            BindDataTestModel(),
            BindDataTestModel(),
        ),
        layoutId = R.layout.activity_recyclerview_test,
        listId = R.id.list,
        titleName = "列表测试页",
        getVMFormActivity = true
    )